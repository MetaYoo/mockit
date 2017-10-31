package com.kotall.mock;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/31 0031 下午 10:05
 * @version: 1.0.0
 */
@Service("payService")
public class PayService {

    @Resource(name = "channelService")
    private ChannelService channelService;

    public String pay(String serviceInfo) {
        return "service:" + channelService.requestBank(serviceInfo);
    }
}
