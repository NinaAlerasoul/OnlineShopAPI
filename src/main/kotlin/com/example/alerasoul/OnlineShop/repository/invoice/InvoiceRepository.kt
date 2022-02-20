package com.example.alerasoul.OnlineShop.repository.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : PagingAndSortingRepository<Invoice, Int> {

    @Query("from Invoice where user.id = :userId")
    fun findAllByUserid(userId: Int, pageable: org.springframework.data.domain.Pageable): List<Invoice>

}