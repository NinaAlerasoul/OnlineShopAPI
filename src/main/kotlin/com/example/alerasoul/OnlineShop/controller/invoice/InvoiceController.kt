package com.example.alerasoul.OnlineShop.controller.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.example.alerasoul.OnlineShop.service.invoice.InvoiceService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/invoice")
class InvoiceController {

    @Autowired
    lateinit var service: InvoiceService


    @GetMapping("/user/{id}")
    fun getAllById(
        @PathVariable id: Int,
        @RequestParam pageIndex: Int,
        @RequestParam pageSize: Int
    ): ServiceResponse<Invoice> {
        return try {
            val data = service.getAllByUserId(id, pageIndex, pageSize) ?: throw NotFoundException("data not found")
            ServiceResponse(data, status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ServiceResponse<Invoice> {
        return try {
            val data = service.getById(id) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PostMapping("/addInvoice")
    fun addInvoice(@RequestBody invoice: Invoice): ServiceResponse<Invoice> {
        return try {
            val data = service.insert(invoice)
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

} 