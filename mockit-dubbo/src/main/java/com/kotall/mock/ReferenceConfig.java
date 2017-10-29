package com.kotall.mock;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import org.easymock.EasyMock;
import org.springframework.beans.factory.FactoryBean;


public class ReferenceConfig implements FactoryBean{

    private String id;
    private String interfaceName;
    private String interfaceCls;
    private String support;
    public static String value;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getInterfaceName()
    {
        return this.interfaceName;
    }

    public void setInterfaceName(String interfaceName)
    {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceCls()
    {
        return this.interfaceCls;
    }

    public void setInterfaceCls(String interfaceCls)
    {
        this.interfaceCls = interfaceCls;
    }

    public String getSupport()
    {
        return this.support;
    }

    public void setSupport(String support)
    {
        this.support = support;
    }

    public Object getObject()
            throws Exception
    {
        return EasyMock.createMock(ReflectUtils.forName(this.getInterfaceCls()));
    }

    public Class<?> getObjectType()
    {
        return ReflectUtils.forName(this.interfaceCls);
    }

    public boolean isSingleton()
    {
        return true;
    }
}
