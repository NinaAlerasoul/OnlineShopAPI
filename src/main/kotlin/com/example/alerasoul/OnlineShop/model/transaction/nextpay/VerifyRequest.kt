package com.example.alerasoul.OnlineShop.model.transaction.nextpay

data class VerifyRequest(
    val apiKey: String,
    val transId: String,
    val amount: Int
)