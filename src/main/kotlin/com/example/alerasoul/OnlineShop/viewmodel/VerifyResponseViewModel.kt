package com.example.alerasoul.OnlineShop.viewmodel

data class VerifyResponseViewModel(
    val status: String,
    val referenceId: String,
    val invoiceNumber: Int,
    val code: Int
)