/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db;

import com.github.aracwong.mockit.db.engine.DbInstance;
import com.github.aracwong.mockit.db.engine.DbEngine;
import com.github.aracwong.mockit.db.exception.MockDbException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : zpwang
 * @version : 1.0.0
 * @date : 2017/12/13
 */
public abstract class AbstractDbEngine  implements DbEngine {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

    /**
     * 实例集合
     */
    private List<DbInstance> instances = Collections.emptyList();

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * 空构造
     */
    public AbstractDbEngine() {}

    /**
     * 添加单个实例
     * @param instance
     */
    public AbstractDbEngine(DbInstance instance) {
        if (this.instances.isEmpty()) {
            this.instances = new ArrayList<>();
        }
        this.instances.add(instance);
    }

    /**
     * 添加多个实例
     * @param instances
     */
    public AbstractDbEngine(List<DbInstance> instances) {
        this.instances = instances;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    @Override
    public void startUp() throws MockDbException {
        for (DbInstance instance : instances) {
            instance.startUp();
        }
    }

    @Override
    public boolean isRunning() {
        int flag = 0;
        for (DbInstance instance : instances) {
            if (instance.isRunning()) {
                flag++;
            }
        }
        return flag == instances.size();
    }

    @Override
    public void refresh() throws MockDbException {
        for (DbInstance instance : instances) {
            instance.refresh();
        }
    }

    public void addDbInstance(DbInstance instance) {
        instances.add(instance);
    }

    public List<DbInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<DbInstance> instances) {
        this.instances = instances;
    }
}
