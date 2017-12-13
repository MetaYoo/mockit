/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : aracwong
 * @date : 2017/10/29 0029 下午 7:54
 * @version : 1.0.0
 */
public class MockDbTest {

    MockDb engine;

    @Before
    public void startUp() throws Exception {
        engine = new MockDb();
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
