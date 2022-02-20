package com.example.alerasoul.OnlineShop.service.product

import com.example.alerasoul.OnlineShop.model.product.ProductCategory
import com.example.alerasoul.OnlineShop.repository.product.ProductCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductCategoryService {

    @Autowired
    lateinit var repository: ProductCategoryRepository

    fun getById(id: Int): ProductCategory? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getAll(): List<ProductCategory> {
        return repository.findAll()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}