package com.kotall.mock;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MockBeanNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new MockBeanDefinitionParser(ReferenceConfig.class));
    }
}
