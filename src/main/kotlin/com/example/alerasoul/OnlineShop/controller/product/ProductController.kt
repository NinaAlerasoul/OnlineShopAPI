package com.example.alerasoul.OnlineShop.controller.product

import com.example.alerasoul.OnlineShop.model.product.Product
import com.example.alerasoul.OnlineShop.service.product.ProductService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api2/product")
class ProductController {

    @Autowired
    lateinit var service: ProductService

    @GetMapping("")
    fun getAll(@RequestParam pageIndex: Int, @RequestParam pageSize: Int): ServiceResponse<Product> {
        return try {
            val data = service.getAll(pageIndex, pageSize)
            ServiceResponse(data, status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ServiceResponse<Product> {
        return try {
            val data = service.getById(id) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/new")
    fun getNewProducts(): ServiceResponse<Product> {
        return try {
            ServiceResponse(data = service.getTopNew(), status = HttpStatus.OK)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }
    @GetMapping("/popular")
    fun getPopularProducts(): ServiceResponse<Product> {
        return try {
            ServiceResponse(data = service.getTopPopular(), status = HttpStatus.OK)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

}