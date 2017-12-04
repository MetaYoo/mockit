package com.github.aracwong.mock;

import com.github.aracwong.mock.bean.ReferenceBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
/**
 * @author aracwong
 */
public class MockBeanNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new MockBeanDefinitionParser(ReferenceBean.class));
    }
}