package com.github.aracwong.mock.db;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/29 0029 下午 7:54
 * @version: 1.0.0
 */
public class DefaultMockDbEngineTest {

    @Test
    public void startUp() throws Exception {
        DefaultMockDBEngine engine = new DefaultMockDBEngine();
        List<String> schemaLists = new ArrayList<>();
        schemaLists.add("test-schema.sql");
        engine.setSchemaLocations(schemaLists);

        List<String> dataLists = new ArrayList<>();
        dataLists.add("test-data.sql");
        engine.setDataLocations(dataLists);

        engine.startUp();
    }

    @Test
    public void testManualPropLocation() throws Exception {
        DefaultMockDBEngine engine = new DefaultMockDBEngine();
        List<String> schemaLists = new ArrayList<>();
        schemaLists.add("test-schema.sql");
        engine.setSchemaLocations(schemaLists);

        List<String> dataLists = new ArrayList<>();
        dataLists.add("test-data.sql");
        engine.setDataLocations(dataLists);

        engine.setPropLocation("test_db.properties");

        engine.startUp();


        String queryStr = "select * from MOCK_TEST where ID = 1";
        Connection conn = engine.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(queryStr);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            String name = rs.getString("USER_NAME");
            Assert.assertEquals("TEST1", name);
        }
        rs.close();
        pstmt.close();
        conn.close();
    }
}
