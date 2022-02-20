package com.example.alerasoul.OnlineShop.model.transaction.nextpay

data class VerifyResponse(
    var code: Int?,
    var amount: Int?,
    var order_id: String?,
    var card_holder: String?,
    var customer_phone: String?,
    var Shaparak_Ref_Id: String?,
    var custom: String?
)