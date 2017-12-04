/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.aracwong.mock;

import com.github.aracwong.mock.bean.ReferenceBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author aracwong
 */
public class MockBeanDefinitionParser implements BeanDefinitionParser {

    private static final String XSD_ID = "id";
    private static final String XSD_INTERFACE = "interface";

    private static final String ID = "id";
    private static final String INTERFACE_CLASS = "interfaceClass";
    private final Class<?> beanClass;

    public MockBeanDefinitionParser(Class<?> beanClass)
    {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        return parse(element, parserContext, this.beanClass);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass)
    {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        if (ReferenceBean.class.equals(beanClass))
        {
            String interfaceName = element.getAttribute(XSD_INTERFACE);
            if ((interfaceName != null) && (interfaceName.length() > 0))
            {
                beanDefinition.setBeanClass(beanClass);
            }
            else
            {
                throw new IllegalStateException("No 'interface' attribute defined.");
            }
        }
        String id = element.getAttribute(XSD_ID);
        String interfaceCls = element.getAttribute(XSD_INTERFACE);

        beanDefinition.getPropertyValues().addPropertyValue(INTERFACE_CLASS, interfaceCls);
        if ((id != null) && (id.length() > 0))
        {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Duplicate spring bean id " + id);
            }
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
            beanDefinition.getPropertyValues().addPropertyValue(ID, id);
        }
        return beanDefinition;
    }
}
