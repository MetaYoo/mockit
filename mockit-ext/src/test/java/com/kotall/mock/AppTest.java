package com.kotall.mock;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class AppTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    WeChatPayService wechatPayService;

    @Test
    public void testMockExt() throws Exception {

        String expectedStr = "2017-11-11";
        EasyMock.expect(paymentService.pay(EasyMock.isA(String.class))).andReturn(expectedStr).times(1);

        EasyMock.replay(paymentService);

        String payResult = this.wechatPayService.pay("arac", "CNY930.00");

        EasyMock.verify(paymentService);
        Assert.assertNotNull(payResult);
        Assert.assertEquals("User: arac payment info: 2017-11-11", payResult);
    }

}
