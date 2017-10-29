package com.kotall.mock.db;

import org.junit.Test;

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
        List<String> schemaLists = new ArrayList<String>();
        schemaLists.add("test-schema.sql");
        engine.setSchemaLocations(schemaLists);

        List<String> dataLists = new ArrayList<String>();
        dataLists.add("test-data.sql");
        engine.setDataLocations(dataLists);

        engine.startUp();
    }
}
