package com.kotall.mock.db;

import com.kotall.mock.db.exception.MockDbException;

import java.sql.Connection;

/**
 * 数据库引擎接口，提供数据库启动 刷新 取得连接及执行语句等功能
 * @Date   Oct 22, 2015
 * @author aracwong
 *
 */
public interface MockDBEngine {
    /**
     * 启动数据库
     * 1. 判断数据库引擎是否正在运行，是则刷新，否则重新加载启动
     * 2. 加载数据库连接等属性
     * 3. 初始化建表语句
     * 4. 向表中插入初始化数据
     * 5. 设置表运行 flag 为 true
     *  Statement:
     *  @throws MockDbException
     */
    void startUp() throws MockDbException;

    /**
     * 返回当前数据库引擎的运行状态
     *  Statement:
     *  @return
     */
    boolean isRunning();

    /**
     * 刷新当前数据库引擎
     *  Statement:
     *  @throws MockDbException
     */
    void refresh() throws MockDbException;

    /**
     * 从数据源中得到数据库连接Connection
     *  Statement:
     *  @return
     *  @throws Exception
     */
    Connection getConnection() throws MockDbException;

    /**
     * 执行数据库操作语句
     *  Statement:
     *  @param statement
     *  @throws MockDbException
     */
    void execute(String statement) throws MockDbException;
}
