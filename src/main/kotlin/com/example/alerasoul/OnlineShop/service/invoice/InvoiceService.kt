package com.example.alerasoul.OnlineShop.service.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.example.alerasoul.OnlineShop.repository.invoice.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class InvoiceService {

    @Autowired
    lateinit var repository: InvoiceRepository

    fun getAllByUserId(userId: Int, pageIndex: Int, pageSize: Int): List<Invoice>? {
        val pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id"))
        return repository.findAllByUserid(userId, pageRequest)
    }

    fun getById(id: Int): Invoice? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun insert(data: Invoice): Invoice {
        return repository.save(data)
    }

    fun update(data: Invoice): Invoice? {
        val oldData = getById(data.id) ?: return null
        oldData.addDate = data.addDate
        oldData.paymentDate = data.paymentDate
        return repository.save(oldData)
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}