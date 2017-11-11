package com.kotall.mock;

import com.kotall.mock.bean.ReferenceBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MockBeanNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new MockBeanDefinitionParser(ReferenceBean.class));
    }
}
