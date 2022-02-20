package com.example.alerasoul.OnlineShop.repository.product

import com.example.alerasoul.OnlineShop.model.product.ProductCategory
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductCategoryRepository : PagingAndSortingRepository<ProductCategory, Int> {

    override fun findAll(): List<ProductCategory>

}