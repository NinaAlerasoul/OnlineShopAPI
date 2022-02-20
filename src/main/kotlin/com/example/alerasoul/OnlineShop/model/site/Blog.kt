package com.example.alerasoul.OnlineShop.model.site

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Blog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var title: String = "",
    var subTitle: String = "",
    var description: String = "",
    var visitCount: Int = 0,
    var addDate: String = ""
)