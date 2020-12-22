package com.honghao.cloud.accountapi.test;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author chenhonghao
 * @date 2020-09-20 22:26
 */
public class CanalTest {

    public static void main(String[] args) throws Exception {
//canal.ip = 192.168.56.104
//canal.port = 11111
//canal.destinations = example
//canal.user =
//canal.passwd =
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.56.104", 11111), "example", "", "");
        try {
            connector.connect();
            //监听的表，    格式为数据库.表名,数据库.表名
            connector.subscribe(".*\\..*");
            connector.rollback();

            while (true) {
                Message message = connector.getWithoutAck(100); // 获取指定数量的数据
                long batchId = message.getId();
                if (batchId == -1 || message.getEntries().isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }
                // System.out.println(message.getEntries());
                printEntries(message.getEntries());
                connector.ack(batchId);// 提交确认，消费成功，通知server删除数据
                // connector.rollback(batchId);// 处理失败, 回滚数据，后续重新获取数据
            }
        } catch (Exception e) {

        } finally {
            connector.disconnect();
        }
    }

    private static void printEntries(List<CanalEntry.Entry> entries) throws Exception {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA) {
                continue;
            }

            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());

            CanalEntry.EventType eventType = rowChange.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                switch (rowChange.getEventType()) {
                    case INSERT:
                        System.out.println("INSERT ");
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case UPDATE:
                        System.out.println("UPDATE ");
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case DELETE:
                        System.out.println("DELETE ");
                        printColumns(rowData.getBeforeColumnsList());
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private static void printColumns(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + " update=" + column.getUpdated());
        }
    }

    @Test
    public void test() {
        int batchSize = 1000;
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "example", "canal", "canal");
        connector.connect();
        connector.rollback();
        while (true) {
            try {
                //尝试从master那边拉去数据batchSize条记录，有多少取多少
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    Thread.sleep(1000);
                } else {
                    System.out.println(111);
                }
                connector.ack(batchId);
                //当队列里面堆积的sql大于一定数值的时候就模拟执行
//                executeQueueSql();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
