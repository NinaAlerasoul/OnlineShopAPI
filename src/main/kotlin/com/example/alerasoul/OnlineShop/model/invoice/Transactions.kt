package com.example.alerasoul.OnlineShop.model.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import javax.persistence.*


@Entity
data class Transactions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var transId: String = "",
    var code: Int = 0,
    var refId: String = "",
    var orderId: String = "",
    var cardHolder: String = "",
    var customerPhone: String = "",
    var ShaparakRefId: String = "",
    var custom: String = "",
    var refundRequest: String = "",
    var amount: Int = 0,


    @ManyToOne
    @JoinColumn(name = "invoice_id")
    val invoice: Invoice? = null
)