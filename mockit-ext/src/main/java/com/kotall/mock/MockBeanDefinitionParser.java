package com.kotall.mock;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class MockBeanDefinitionParser implements BeanDefinitionParser {

    private static final String XSD_ID = "id";
    private static final String XSD_NAME = "name";
    private static final String XSD_INTERFACE = "interface";

    private static final String ID = "id";
    private static final String INTERFACE_NAME = "interfaceName";
    private static final String INTERFACE_CLASS = "interfaceClass";
    private final Class<?> beanClass;

    public MockBeanDefinitionParser(Class<?> beanClass)
    {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        return parse(element, parserContext, this.beanClass);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass)
    {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        if (ReferenceConfig.class.equals(beanClass))
        {
            String interfaceName = element.getAttribute("interface");
            if ((interfaceName != null) && (interfaceName.length() > 0))
            {
                ReferenceConfig.value = interfaceName;
                beanDefinition.setBeanClass(beanClass);
            }
            else
            {
                throw new IllegalStateException("No 'interface' attribute defined.");
            }
        }
        String id = element.getAttribute(XSD_ID);
        String name = element.getAttribute(XSD_NAME);
        String interfaceCls = element.getAttribute(XSD_INTERFACE);
        beanDefinition.getPropertyValues().addPropertyValue(INTERFACE_NAME, name);
        beanDefinition.getPropertyValues().addPropertyValue(INTERFACE_CLASS, interfaceCls);
        if ((id == null) || (id.length() == 0))
        {
            String generatedBeanName = name;
            if ((generatedBeanName == null) || (generatedBeanName.length() == 0)) {
                generatedBeanName = interfaceCls;
                // generatedBeanName = beanClass.getName();
            }
        }
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
