/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author : zpwang
 * @version : 1.0.0
 * @date : 2017/12/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mockdb-test.xml"})
public class SpringMockDbTest {

    @Test
    public void testSpringMockOracle() throws Exception {

        Class.forName("org.h2.Driver");

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:TEST2;MODE=Oracle;DB_CLOSE_DELAY=-1", "root", "123456");
        String queryStr = "select * from MOCK_TEST where ID = 2";
        PreparedStatement pstmt = conn.prepareStatement(queryStr);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            String name = rs.getString("USER_NAME");
            Assert.assertEquals("TEST2", name);
        }
        rs.close();
        pstmt.close();
        conn.close();
    }

}
