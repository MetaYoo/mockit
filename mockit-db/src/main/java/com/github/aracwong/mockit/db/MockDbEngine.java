/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db;

import com.github.aracwong.mockit.db.exception.MockDbException;

import java.sql.Connection;

/**
 * 数据库引擎接口，提供数据库启动 刷新 取得连接及执行语句等功能
 * @Date : Oct 22, 2015
 * @author :  aracwong
 * @since :  1.0.0
 */
public interface MockDbEngine {
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
