package com.honghao.cloud.txmanagestarter.config;

import javax.sql.DataSource;

/**
 * @author chenhonghao
 * @date 2020-07-13 15:09
 */
public class TxManagerService {
    private DataSource dataSource;;

    public TxManagerService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
