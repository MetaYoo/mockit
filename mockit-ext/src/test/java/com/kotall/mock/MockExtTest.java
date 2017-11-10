package com.kotall.mock;

import com.kotall.mock.service.ChannelService;
import com.kotall.mock.service.PaymentService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class MockExtTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ChannelService channelService;

    @Test
    public void testMockExt() throws Exception {

        String expectedStr = "2017-11-11";
        EasyMock.expect(channelService.request(EasyMock.isA(String.class))).andReturn(expectedStr).times(1);

        EasyMock.replay(channelService);

        String payResult = this.paymentService.pay("CNY930.00");

        EasyMock.verify(channelService);
        Assert.assertNotNull(payResult);
        Assert.assertEquals("PAY_RESULT:2017-11-11", payResult);
    }

}
