package com.example.alerasoul.OnlineShop.model.transaction.nextpay


data class TokenResponse (
    val code: Int,
    val trans_id: String,
    val amount: Int
        ) 