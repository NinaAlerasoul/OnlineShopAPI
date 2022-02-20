package com.example.alerasoul.OnlineShop.repository.product

import com.example.alerasoul.OnlineShop.model.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int> {

    override fun findAll(): List<Product>

    fun findTop6ByOrderByAddDateDesc(): List<Product>

    fun findTop6ByOrderByVisitCountDesc(): List<Product>

    @Query("select price from Product where id = :id")
    fun getFirstPriceById(id: Int): Long

    @Query("from Product where id in :idList")
    fun findAllByIdList(idList: List<Int>): List<Product>

    @Query(
        value = "select * from Product where product_category_id =?1",
        countQuery = "select count(*) from Product where product_category_id =?1",
        nativeQuery = true
    )

    fun findAllByCategoryId(categoryId: Int, pageable: Pageable): Page<Product>

}