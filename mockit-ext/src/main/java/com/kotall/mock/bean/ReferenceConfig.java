package com.kotall.mock.bean;

import java.io.Serializable;
/**
 * @author aracwong
 */
public class ReferenceConfig implements Serializable {

    private String id;
    private Class<?> interfaceClass;

    public ReferenceConfig() {
    }

    public String getId() {
        if (null == id || id.length() == 0) {
            return this.interfaceClass.getName();
        }
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
        return interfaceClass;
    }

}
