package com.example.alerasoul.OnlineShop.controller.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.example.alerasoul.OnlineShop.service.invoice.InvoiceService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.UserUtil.Companion.getCurrentUsername
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.config.JwtTokenUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api2/invoice")
class InvoiceController {

    @Autowired
    lateinit var service: InvoiceService

    @Autowired
    lateinit var jwtTokenUtils: JwtTokenUtils

    @GetMapping("/user/{id}")
    fun getAllById(
        @PathVariable userId: Int,
        @RequestParam pageIndex: Int,
        @RequestParam pageSize: Int,
        request: HttpServletRequest
    ): ServiceResponse<Invoice> {
        return try {
            val currentUser = getCurrentUsername(request, jwtTokenUtils)
            val data = service.getAllByUserId(userId, pageIndex, pageSize, currentUser)
                ?: throw NotFoundException("data not found")
            ServiceResponse(data, status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int, request: HttpServletRequest): ServiceResponse<Invoice> {
        return try {
            val currentUser = getCurrentUsername(request, jwtTokenUtils)
            val data = service.getById(id, currentUser) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PostMapping("/addInvoice")
    fun addInvoice(@RequestBody invoice: Invoice, request: HttpServletRequest): ServiceResponse<Invoice> {
        return try {
            val currentUser = getCurrentUsername(request, jwtTokenUtils)
            val data = service.insert(invoice, currentUser)
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

} 