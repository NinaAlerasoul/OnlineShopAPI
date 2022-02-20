package com.example.alerasoul.OnlineShop.model.product

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
data class ProductCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val title: String = "",
    val image: String = "",

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    var products: Set<Product>? = null

)