package com.example.alerasoul.OnlineShop.service.product

import com.example.alerasoul.OnlineShop.model.product.Product
import com.example.alerasoul.OnlineShop.repository.product.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService {

    @Autowired
    lateinit var repository: ProductRepository

    fun getById(id: Int): Product? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getAll(pageIndex: Int, pageSize: Int): List<Product> {
        val pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id"))
        return repository.findAll(pageRequest).toList()
    }

    fun getTopNew(): List<Product> {
        return repository.findTop6ByOrderByAddDateDesc()
    }

    fun getTopPopular(): List<Product> {
        return repository.findTop6ByOrderByVisitCountDesc()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

    fun getPriceById(id: Int): Long {
        return repository.getFirstPriceById(id)
    }

    fun getAllByIdList(idList: List<Int>): List<Product> {
        return repository.findAllByIdList(idList)
    }

    fun getAllByCategoryId(categoryId: Int, pageIndex: Int, pageSize: Int): List<Product> {
        val pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id"))
        return repository.findAllByCategoryId(categoryId, pageRequest).toList()
    }

}