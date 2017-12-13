/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.aracwong.mockit.ext.bean;

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
        return EasyMock.createMock(ReflectUtils.forName(super.getInterfaceClass().getName()));
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
