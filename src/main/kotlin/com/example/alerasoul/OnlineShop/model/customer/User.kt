package com.example.alerasoul.OnlineShop.model.customer

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var username: String = "",
    var password: String = "",

    @OneToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    var invoices: Set<Invoice>? = null
)