package com.example.alerasoul.OnlineShop.model.customer

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var phone: String = "",
    var postalCode: String = "",

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    var user: User? = null
)