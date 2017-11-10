package com.kotall.mock.service.impl;

import com.kotall.mock.service.ChannelService;
import com.kotall.mock.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    @Autowired
    private ChannelService channelService;
    
    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/
    
    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    @Override
    public String pay(String trxAmt) {
        return "PAY_RESULT:" + this.channelService.request(trxAmt);
    }
}
