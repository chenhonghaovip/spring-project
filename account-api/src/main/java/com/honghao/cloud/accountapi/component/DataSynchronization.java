package com.honghao.cloud.accountapi.component;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.honghao.cloud.accountapi.config.DataSynchronizationConfig;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * canal数据同步组件
 *
 * @author chenhonghao
 * @date 2020-09-21 11:17
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DataSynchronizationConfig.class)
public class DataSynchronization {
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
    private static Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();
    private static CanalConnector connector;

    public DataSynchronization(DataSynchronizationConfig data) {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            try {
                if (connector==null){
                    connector = CanalConnectors.newSingleConnector(new InetSocketAddress(data.getCanalAddress(),
                            data.getPort()), data.getDestination(), data.getUserName(), data.getPassword());
                    connector.connect();
                    connector.rollback();
                    init();
                }
            } catch (CanalClientException e) {
                log.error(e.getMessage());
                connector = null;
            }
        },0,10, TimeUnit.SECONDS);
    }

    public void init(){
        int batchSize = 1000;
        while (true) {
            try {
                //尝试从master那边拉去数据batchSize条记录，有多少取多少
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    Thread.sleep(1000);
                } else {
                    dataHandle(message.getEntries());
                }
                connector.ack(batchId);
                //当队列里面堆积的sql大于一定数值的时候就模拟执行
                executeQueueSql();
            } catch (Exception e){
                connector = null;
                log.error(e.getMessage());
                return;
            }
        }
    }


    /**
     * 模拟执行队列里面的sql语句
     */
    private static void executeQueueSql() {
        int size = SQL_QUEUE.size();
        for (int i = 0; i < size; i++) {
            String sql = SQL_QUEUE.poll();
            System.out.println("[sql]----> " + sql);
        }
    }

    /**
     * 数据处理
     *
     * @param entrys entrys
     */
    private static void dataHandle(List<CanalEntry.Entry> entrys) throws InvalidProtocolBufferException {
        for (CanalEntry.Entry entry : entrys) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                if (eventType == CanalEntry.EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    saveInsertSql(entry);
                }
            }
        }
    }

    /**
     * 保存更新语句
     *
     * @param entry entry
     */
    private static void saveUpdateSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> newColumnList = rowData.getAfterColumnsList();
                StringBuilder sql = new StringBuilder("update " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " set ");
                for (int i = 0; i < newColumnList.size(); i++) {
                    sql.append(" ").append(newColumnList.get(i).getName()).append(" = '").append(newColumnList.get(i).getValue()).append("'");
                    if (i != newColumnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" where ");
                List<CanalEntry.Column> oldColumnList = rowData.getBeforeColumnsList();
                for (CanalEntry.Column column : oldColumnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName()).append("=").append(column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存删除语句
     *
     * @param entry entry
     */
    private static void saveDeleteSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> columnList = rowData.getBeforeColumnsList();
                StringBuilder sql = new StringBuilder("delete from " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " where ");
                for (CanalEntry.Column column : columnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName()).append("=").append(column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存插入语句
     * @param entry entry
     */
    private static void saveInsertSql(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> columnList = rowData.getAfterColumnsList();
                StringBuilder sql = new StringBuilder("insert into " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append(columnList.get(i).getName());
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(") VALUES (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append("'").append(columnList.get(i).getValue()).append("'");
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
