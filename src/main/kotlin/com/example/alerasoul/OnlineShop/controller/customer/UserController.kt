package com.example.alerasoul.OnlineShop.controller.customer


import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.service.customer.UserService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.viewmodel.UserViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/user")
class UserController {

    @Autowired
    lateinit var service: UserService

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ServiceResponse<User> {
        return try {
            val data = service.getById(id) ?: throw NotFoundException("data not found")
            ServiceResponse(data = listOf(data), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PostMapping("/register")
    fun addUser(@RequestBody user: UserViewModel): ServiceResponse<User> {
        return try {
            val data = service.insert(user.convertToUser())
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody user: UserViewModel): ServiceResponse<User> {
        return try {
            val data = service.getByUsernameAndPass(user.username, user.password)!!
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PutMapping("/update")
    fun editUser(@RequestBody user: UserViewModel): ServiceResponse<User>? {
        return try {
            val data = service.update(user.convertToUser()) ?: throw NotFoundException("data not found")
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PutMapping("/changePass")
    fun changePassword(@RequestBody user: UserViewModel): ServiceResponse<User> {
        return try {

            val data = service.changePassword(user.convertToUser(), user.repeatPass, user.oldPass)
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

}