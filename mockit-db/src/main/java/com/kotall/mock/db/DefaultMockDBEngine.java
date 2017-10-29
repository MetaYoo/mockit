package com.kotall.mock.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.Preconditions;
import com.kotall.mock.db.exception.MockDbException;
import com.kotall.mock.util.FileKit;
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
 * @author aracwong
 * @Date Oct 22, 2015
 */

public class DefaultMockDBEngine implements MockDBEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMockDBEngine.class);

    private boolean isRunning = false;

    private DataSource dataSource;

    private Properties props;

    private List<String> schemaLocations = Collections.emptyList();

    private List<String> dataLocations = Collections.emptyList();

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
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("META-INF/h2_db.properties");
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
}
