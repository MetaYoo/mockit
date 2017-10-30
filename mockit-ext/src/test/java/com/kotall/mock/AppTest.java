package com.kotall.mock;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

    @Test
    public void setUp() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-test.xml");
        PaymentService paymentService = (PaymentService) ctx.getBean("paymentService");
        paymentService.pay(11.0d);

    }
}
