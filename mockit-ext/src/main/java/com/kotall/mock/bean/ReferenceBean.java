package com.kotall.mock.bean;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import org.easymock.EasyMock;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/11/11 0011 上午 10:47
 * @version: 1.0.0
 */
public class ReferenceBean extends ReferenceConfig implements FactoryBean {

    public ReferenceBean() {
        super();
    }

    @Override
    public Object getObject() throws Exception {
        return EasyMock.createMock(ReflectUtils.forName(super.getInterfaceName()));
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
