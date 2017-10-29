package com.kotall.mock.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.Preconditions;
import com.kotall.mock.util.XmlKit;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * 数据库引擎接口，提供数据库启动 刷新 取得连接及执行语句等功能
 * @Date   Oct 22, 2015
 * @author aracwong
 *
 */

public class DefaultMockDBEngine implements MockDBEngine {

    private boolean isRunning =false;

    private DataSource dataSource;

    private Properties props;

    private List<String> schemaLocations;

    private List<String> dataLocations;

    public DefaultMockDBEngine() {
    }

    @Override
    public void startUp() throws Exception {
        if (isRunning()) {
            refresh();
            return;
        }
        loadDbProps();
        loadSchema();
        loadInitData();
        isRunning = true;
    }

    public void loadDbProps() throws Exception {
        props = new Properties();
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("META-INF/h2_db.properties");
        props.load(is);
        if(null != is) {
            is.close();
        }
    }

    public void loadSchema() throws Exception {
        for (String schemaXml : this.getSchemaLocations()) {
            String sqlStr = loadSource(schemaXml);
            this.execute(sqlStr);
        }
    }

    public String loadSource(String configPath) throws Exception {
        InputStream is = this.getClass()
                .getResourceAsStream(configPath);
        String str = XmlKit.parseXmlStr(is);
        if(null != is) {
            is.close();
        }
        return str;
    }

    public void loadInitData() throws Exception {
        for (String dataXml : this.getDataLocations()) {
            String sqlStr = loadSource(dataXml);
            this.execute(sqlStr);
        }
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public void refresh() throws Exception {
        if(isRunning()) {
            loadInitData();
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        Preconditions.checkNotNull(this.getDataSource());
        return this.getDataSource().getConnection();
    }

    @Override
    public void execute(String sql) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = this.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } finally {
            if(null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
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


}
