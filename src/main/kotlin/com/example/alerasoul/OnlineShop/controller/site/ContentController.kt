package com.example.alerasoul.OnlineShop.controller.site

import com.example.alerasoul.OnlineShop.model.site.Content
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.service.site.ContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api2/content")
class ContentController {
    @Autowired
    lateinit var service: ContentService

    @GetMapping("")
    fun getAll(): ServiceResponse<Content> {
        return try {
            ServiceResponse(data = service.getAll(), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/{title}")
    fun getByTitle(@PathVariable title: String): ServiceResponse<Content> {
        return try {
            val data = service.getByTitle(title) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }
} 