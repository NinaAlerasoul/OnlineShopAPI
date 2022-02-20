package com.example.alerasoul.OnlineShop.config.payment

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.beans.ConstructorProperties

@Configuration
@ConfigurationProperties("payment.nextpay")
class NextPayConfig {

    var apiKey: String = ""
    var tokenEndPoint: String = ""
    var callbackUri: String = ""
    var paymentUri: String = ""
    var verifyUri: String = ""

} 