/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.aracwong.mock;

import com.github.aracwong.mock.service.PaymentService;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;

/**
 *  http://blog.csdn.net/fei33423/article/details/46653501
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/30 0030 下午 9:02
 * @version: 1.0.0
 */
public class JMockitTest {

    @Test
    public void testMockUp() {
        PaymentService paymentService = new MockUp<PaymentService>() {
            @Mock
            public String pay(String amt) {
                return "pay money: " + amt;
            }
        }.getMockInstance();

        Assert.assertNotNull(paymentService);
        String rs = paymentService.pay("CNY930.00");
        Assert.assertEquals("pay money: CNY930.00", rs);
    }




}
