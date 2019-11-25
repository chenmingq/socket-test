package com.github.chenmingq.server.basic.db;

import java.sql.Connection;

public interface ConnectionPool {

    /**
     * 与特定数据库的连接（会话）。
     *
     * @return
     */
    Connection getConnection();

    /**
     * 关闭连接
     *
     * @param connection
     */
    void releaseConn(Connection connection);


    /**
     * 创建数据连接
     *
     * @param initDbPool
     */
    void createDataSource(boolean initDbPool);


}
