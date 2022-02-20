package com.example.alerasoul.OnlineShop.repository.product

import com.example.alerasoul.OnlineShop.model.product.Color
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ColorRepository : PagingAndSortingRepository<Color, Int> {

    override fun findAll(): List<Color>

}