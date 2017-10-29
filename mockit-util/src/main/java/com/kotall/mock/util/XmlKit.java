package com.kotall.mock.util;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * xml解析工具类
 * @Date   Oct 22, 2015
 * @author aracwong
 *
 */
public class XmlKit {

    public static String parseXmlStr(InputStream input)
            throws Exception {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document xmlDoc = db.parse(input);
        return xmlDoc.getTextContent();
    }
}
