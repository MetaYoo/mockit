/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db;

import com.github.aracwong.mockit.db.annotation.Function;
import com.github.aracwong.mockit.db.constant.DbType;
import com.github.aracwong.mockit.db.engine.DbEngine;
import com.github.aracwong.mockit.db.exception.MockDbException;
import com.github.aracwong.mockit.db.function.MysqlFunction;
import com.github.aracwong.mockit.db.function.OracleFunction;
import com.github.aracwong.mockit.util.FileKit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : zpwang
 * @version : 1.0.0
 * @date : 2017/12/13
 */
public abstract class AbstractDbEngine implements DbEngine {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /**
     * H2 驱动
     */
    private static final String H2_DRIVER = "org.h2.Driver";

    /**
     * 默认模式
     */
    private static final String DEFAULT_MODE = "MYSQL";

    /**
     * 默认用户
     */
    private static final String DEFAULT_USER = "root";

    /**
     * 默认密码
     */
    private static final String DEFAULT_PASSWORD = "";
    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

    /**
     * 数据库模式
     */
    private String mode;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库用户名
     */
    private String user;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 是否在运行
     */
    private boolean isRunning = false;

    /**
     * DDL 脚本
     */
    private List<String> schemaLocations = Collections.emptyList();

    /**
     * DDL脚本
     */
    private List<String> dataLocations = Collections.emptyList();
    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * 空构造
     */
    public AbstractDbEngine() {}

    /**
     *
     * @param mode
     */
    public AbstractDbEngine(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @param mode
     * @param dbName
     */
    public AbstractDbEngine(String mode, String dbName) {
        this.mode = mode;
        this.dbName = dbName;
    }

    /**
     *
     * @param mode
     * @param dbName
     * @param user
     */
    public AbstractDbEngine(String mode, String dbName, String user) {
        this.mode = mode;
        this.dbName = dbName;
        this.user = user;
    }

    /**
     *
     * @param mode
     * @param dbName
     * @param user
     * @param password
     */
    public AbstractDbEngine(String mode, String dbName, String user, String password) {
        this.mode = mode;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    @Override
    public void startUp() throws MockDbException {
        try {
            if (isRunning()) {
                refresh();
                return;
            }
            initSchema();
            initCustomizedFunction();
            initData();
            isRunning = true;
        } catch (Exception e) {
            throw new MockDbException("failed to start up mock db " + e.getMessage(), e);
        }
    }


    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void refresh() throws MockDbException {
        try {
            if (isRunning()) {
                initData();
            }
        } catch (Exception e) {
            throw new MockDbException("failed to refresh mock db " + e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection() throws MockDbException {
        StringBuffer sb = new StringBuffer(100);
        sb.append("jdbc:h2:mem:")
                .append(null == dbName ? DEFAULT_MODE : dbName)
                .append(";MODE=")
                .append(null == mode ? DEFAULT_MODE : mode)
                .append(";DB_CLOSE_DELAY=-1");
        try {
            Class.forName(H2_DRIVER);
            return DriverManager.getConnection(sb.toString(), null == user ? DEFAULT_USER : user, null == password ? DEFAULT_PASSWORD : password);
        } catch (ClassNotFoundException e) {
            throw new MockDbException("default driver not support:" + H2_DRIVER, e);
        } catch (SQLException e) {
            throw new MockDbException("failed to create connection", e);
        }
    }

    @Override
    public void execute(String sql) throws MockDbException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            throw new MockDbException("failed to execute script:" + sql, e);
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (Exception e) {
                // do nothing.
            }
        }
    }

    /**
     * 自定义函数
     */
    private void initCustomizedFunction() throws Exception {
        List<String> funcSqlList = new ArrayList<>();
        String mode = null == this.mode ? DEFAULT_MODE : this.mode;
        if (DbType.MYSQL.getName().equals(mode)) {
            Method[] methods = MysqlFunction.class.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Function) {
                        String funcName = ((Function)annotation).name();
                        String methodName = MysqlFunction.class.getName() + "." + method.getName();
                        String sql = "CREATE ALIAS IF NOT EXISTS " + funcName + " FOR \"" + methodName + "\"; ";
                        funcSqlList.add(sql);
                    }
                }

            }
        } else if (DbType.ORACLE.getName().equals(mode)) {
            Method[] methods = OracleFunction.class.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Function) {
                        String funcName = ((Function)annotation).name();
                        String methodName = OracleFunction.class.getSimpleName() + "." + method.getName();
                        String sql = "CREATE ALIAS IF NOT EXISTS " + funcName + " FOR \"" + methodName + "\"; ";
                        funcSqlList.add(sql);
                    }
                }

            }
        }

        for (String sql : funcSqlList) {
            this.execute(sql);
        }
    }

    private void initSchema() throws Exception {
        for (String schemaScript : this.getSchemaLocations()) {
            String sqlStr = FileKit.readText(schemaScript);
            this.execute(sqlStr);
        }
    }

    private void initData() throws Exception {
        for (String dataScript : this.getDataLocations()) {
            String sqlStr = FileKit.readText(dataScript);
            this.execute(sqlStr);
        }
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSchemaLocations() {
        return schemaLocations;
    }

    public void setSchemaLocations(List<String> schemaLocations) {
        this.schemaLocations = schemaLocations;
    }

    public List<String> getDataLocations() {
        return dataLocations;
    }

    public void setDataLocations(List<String> dataLocations) {
        this.dataLocations = dataLocations;
    }
}
