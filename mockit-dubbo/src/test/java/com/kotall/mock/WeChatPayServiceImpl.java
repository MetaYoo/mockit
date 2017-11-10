package com.kotall.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wechatPayService")
public class WeChatPayServiceImpl implements WeChatPayService {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/

    @Autowired
    private PaymentService paymentService;
    
    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/
    
    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    @Override
    public String pay(String userId, String isoAmt) {
        return "User: " + userId + " payment info: " + this.paymentService.pay(isoAmt);
    }
}
