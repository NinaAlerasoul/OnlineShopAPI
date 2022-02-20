package com.example.alerasoul.OnlineShop.repository.customer

import com.example.alerasoul.OnlineShop.model.customer.Customer
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : PagingAndSortingRepository<Customer, Int> {

    override fun findAll(): List<Customer>

}