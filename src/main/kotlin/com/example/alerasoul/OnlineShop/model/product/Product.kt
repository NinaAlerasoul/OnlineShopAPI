package com.example.alerasoul.OnlineShop.model.product

import org.hibernate.annotations.ColumnDefault
import javax.persistence.*


@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var description: String = "",
    var image: String = "",
    var visitCount: Int = 0,
    var addDate: String = "",
    var title: String = "",

    @ColumnDefault(value = "0")
    var price:Long=0,

    @ManyToMany
    var size: Set<Size>? = null,

    @ManyToMany
    var colors: Set<Color>? = null,

    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    var category: ProductCategory? = null
)