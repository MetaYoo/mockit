package com.github.aracwong.mock.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/29 0029 下午 7:40
 * @version: 1.0.0
 */
public class FileKit {

    public static String readText(String path) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = br.readLine();
            while (null != tmp) {
                sb.append(tmp).append("\r\n");
                tmp = br.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}
