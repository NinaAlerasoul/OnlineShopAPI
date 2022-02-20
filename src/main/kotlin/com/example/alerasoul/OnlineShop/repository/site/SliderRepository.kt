package com.example.alerasoul.OnlineShop.repository.site

import com.example.alerasoul.OnlineShop.model.site.Slider
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SliderRepository : PagingAndSortingRepository<Slider, Int> {
    override fun findAll(): List<Slider>
}