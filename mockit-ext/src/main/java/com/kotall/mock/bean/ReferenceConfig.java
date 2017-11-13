package com.kotall.mock.bean;

import java.io.Serializable;
/**
 * @author aracwong
 */
public class ReferenceConfig implements Serializable {

    private String id;
    private String interfaceName;
    private Class<?> interfaceClass;
    public static String value;

    public ReferenceConfig() {
    }

    public String getId() {
        if (null == id || id.length() == 0) {
            return this.getInterfaceName();
        }
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        if (null == this.interfaceName || "".equals(this.interfaceName.trim())) {
            return interfaceClass.getName();
        }
        return this.interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        if (interfaceClass != null && !interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        this.interfaceClass = interfaceClass;
    }

    public Class<?> getInterfaceClass() {
        if (interfaceClass != null) {
            return interfaceClass;
        }
        try {
            if (interfaceName != null && interfaceName.length() > 0) {
                this.interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                        .getContextClassLoader());
            }
        } catch (ClassNotFoundException t) {
            throw new IllegalStateException(t.getMessage(), t);
        }
        return interfaceClass;
    }

}
