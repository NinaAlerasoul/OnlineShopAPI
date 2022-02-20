package com.example.alerasoul.OnlineShop.service.invoice


import com.example.alerasoul.OnlineShop.model.invoice.InvoiceItems
import com.example.alerasoul.OnlineShop.repository.invoice.InvoiceItemsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InvoiceItemService {

    @Autowired
    lateinit var repository: InvoiceItemsRepository

    fun getAllByInvoiceId(invoiceId: Int): List<InvoiceItems>? {
        return repository.findAllByInvoiceId(invoiceId)
    }

    fun getById(id: Int): InvoiceItems? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}