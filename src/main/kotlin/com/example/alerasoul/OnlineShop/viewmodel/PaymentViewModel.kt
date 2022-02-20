package com.example.alerasoul.OnlineShop.viewmodel

import com.example.alerasoul.OnlineShop.model.invoice.InvoiceItems

data class PaymentViewModel(
    val user: UserViewModel,
    val items: List<InvoiceItems>
) 