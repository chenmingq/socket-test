package com.github.chenmingq.server.basic.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.utils.LordPropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DruidSourceConnectionPool implements ConnectionPool {

    private DruidDataSource druidDataSource = null;

    private static DruidSourceConnectionPool instance = new DruidSourceConnectionPool();

    public static DruidSourceConnectionPool getInstance() {
        return instance;
    }

    public DruidSourceConnectionPool() {
    }


    @Override
    public Connection getConnection() {
        DruidPooledConnection connection = null;
        try {
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void releaseConn(Connection connection) {
        try {
            if (null == connection) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            log.error("{}", e.getMessage());
        }
    }

    @Override
    public void createDataSource(boolean initDb) {
        if (!initDb) {
            return;
        }
        Properties properties = LordPropertiesUtils.lordProperties(CommonConst.DB_SERVER_PROPERTIES);

        if (null == properties) {
            throw new NullPointerException("db_server_properties is null");
        }
        try {
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            log.info("{}", druidDataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
