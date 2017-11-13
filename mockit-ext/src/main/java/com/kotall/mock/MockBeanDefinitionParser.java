package com.kotall.mock;

import com.kotall.mock.bean.ReferenceBean;
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
                ReferenceBean.value = interfaceName;
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
