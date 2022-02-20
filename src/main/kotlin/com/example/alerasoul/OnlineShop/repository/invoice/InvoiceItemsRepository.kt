package com.example.alerasoul.OnlineShop.repository.invoice

import com.example.alerasoul.OnlineShop.model.invoice.InvoiceItems
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceItemsRepository : PagingAndSortingRepository<InvoiceItems, Int> {

    override fun findAll(): List<InvoiceItems>

    @Query("from InvoiceItems where invoice.id = :invoiceId")
    fun findAllByInvoiceId(invoiceId: Int): List<InvoiceItems>

}