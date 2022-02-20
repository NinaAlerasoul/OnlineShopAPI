package com.example.alerasoul.OnlineShop.model.invoice


import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.model.enum.InvoiceStatus
import javax.persistence.*

@Entity
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var status: InvoiceStatus? = null,
    var addDate: String = "",
    var paymentDate: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToMany(mappedBy = "invoice")
    var invoiceItems: List<InvoiceItems>? = null,

    @OneToMany(mappedBy = "invoice")
    var transactions: Set<Transactions>? = null
)