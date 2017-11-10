package com.kotall.mock;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

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
            public String pay(double amt) {
                return "pay money: " + amt;
            }
        }.getMockInstance();

        Assert.assertNotNull(paymentService);
        String rs = paymentService.pay(100L);
        Assert.assertEquals("pay money: 100.0", rs);
    }




}
