package com.kotall.mock;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/31 0031 下午 10:06
 * @version: 1.0.0
 */
@Component("payBiz")
public class PayBiz {

    @Resource(name = "payService")
    private PayService payService;

    public String pay(String bizInfo) {
        return "Biz: " + payService.pay(bizInfo);
    }
}
