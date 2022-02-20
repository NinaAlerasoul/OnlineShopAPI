package com.example.alerasoul.OnlineShop.controller.site

import com.example.alerasoul.OnlineShop.model.site.Blog
import com.example.alerasoul.OnlineShop.service.site.BlogService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api2/blog")
class BlogController {

    @Autowired
    lateinit var service: BlogService

    @GetMapping("")
    fun getAll(): ServiceResponse<Blog> {
        return try {
            ServiceResponse(data = service.getAll(), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            return ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            return ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ServiceResponse<Blog> {
        return try {
            val data = service.getById(id) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            return ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            return ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

} 