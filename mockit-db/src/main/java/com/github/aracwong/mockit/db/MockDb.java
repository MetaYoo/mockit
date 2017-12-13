/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.aracwong.mockit.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.aracwong.mockit.db.engine.DbEngine;
import com.github.aracwong.mockit.db.exception.MockDbException;
import com.google.common.base.Preconditions;
import com.github.aracwong.mockit.util.FileKit;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 数据库引擎接口，提供数据库启动 刷新 取得连接及执行语句等功能
 *
 * @author : aracwong
 * @Date : Oct 22, 2015
 * @since : 1.0.0
 */

public class MockDb implements DbEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDb.class);

    private boolean isRunning = false;

    private DataSource dataSource;

    private Properties props;

    private List<String> schemaLocations = Collections.emptyList();

    private List<String> dataLocations = Collections.emptyList();

    private String propLocation;

    @Override
    public void startUp() throws MockDbException {
        try {
            if (isRunning()) {
                refresh();
                return;
            }
            loadDbProps();
            loadSchema();
            loadInitData();
            isRunning = true;
        } catch (Exception e) {
            throw new MockDbException("failed to start up mock db " + e.getMessage(), e);
        }

    }

    public void loadDbProps() throws Exception {
        props = new Properties();
        String propFilePath = "META-INF/h2_db.properties";
        if (!StringUtils.isNullOrEmpty(this.propLocation)) {
            propFilePath = this.propLocation;
        }
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(propFilePath);
        props.load(is);
        if (null != is) {
            is.close();
        }
    }

    public void loadSchema() throws Exception {
        for (String schemaScript : this.getSchemaLocations()) {
            String sqlStr = loadSource(schemaScript);
            this.execute(sqlStr);
        }
    }

    public String loadSource(String configPath) throws Exception {
        return FileKit.readText(configPath);
    }

    public void loadInitData() throws Exception {
        for (String dataScript : this.getDataLocations()) {
            String sqlStr = loadSource(dataScript);
            this.execute(sqlStr);
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
                loadInitData();
            }
        } catch (Exception e) {
            throw new MockDbException("failed to refresh mock db " + e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection() throws MockDbException {
        try {
            Preconditions.checkNotNull(this.getDataSource());
            return this.getDataSource().getConnection();
        } catch (Exception e) {
            throw new MockDbException("failed to get Connection" + e.getMessage(), e);
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


    public DataSource getDataSource() throws Exception {
        if (dataSource == null) {
            dataSource = DruidDataSourceFactory.createDataSource(props);
        }
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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

    public String getPropLocation() {
        return propLocation;
    }

    public void setPropLocation(String propLocation) {
        this.propLocation = propLocation;
    }
}
