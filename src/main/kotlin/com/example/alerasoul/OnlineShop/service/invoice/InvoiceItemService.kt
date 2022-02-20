package com.example.alerasoul.OnlineShop.service.invoice


import com.example.alerasoul.OnlineShop.model.invoice.InvoiceItems
import com.example.alerasoul.OnlineShop.repository.invoice.InvoiceItemsRepository
import com.example.alerasoul.OnlineShop.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InvoiceItemService {

    @Autowired
    lateinit var repository: InvoiceItemsRepository

    @Autowired
    lateinit var productService: ProductService


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

    fun addItem(data: InvoiceItems): InvoiceItems {
        if (data.quantity <= 0)
            throw Exception("invalid quantity")
        if (data.product!!.id <= 0 || data.product?.id == null)
            throw Exception("invalid product id")

        var productData = productService.getById(data.product!!.id)
        data.unitPrice = productData!!.price
        return repository.save(data)
    }

}