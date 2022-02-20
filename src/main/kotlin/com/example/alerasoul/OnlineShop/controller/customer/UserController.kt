package com.example.alerasoul.OnlineShop.controller.customer


import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.service.customer.UserService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.UserUtil.Companion.getCurrentUsername
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.viewmodel.UserViewModel
import com.example.alerasoul.OnlineShop.config.JwtTokenUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api2/user")
class UserController {

    @Autowired
    lateinit var service: UserService

    @Autowired
    lateinit var jwtUtil: JwtTokenUtils

    @GetMapping()
    fun getById(request: HttpServletRequest): ServiceResponse<User> {
        return try {
            val currentUser = getCurrentUsername(request, jwtUtil)
            val data = service.getUserByUsername(currentUser) ?: throw NotFoundException("data not found")
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
    fun login(@RequestBody user: UserViewModel): ServiceResponse<UserViewModel> {
        return try {
            val data =
                service.getByUsernameAndPass(user.username, user.password) ?: throw NotFoundException("data not found")
            val vm = UserViewModel(data)
            vm.token = jwtUtil.generateToken(vm)!!
            ServiceResponse(listOf(vm), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PutMapping("/update")
    fun editUser(@RequestBody user: UserViewModel, request: HttpServletRequest): ServiceResponse<User>? {
        return try {
            val currentUser = getCurrentUsername(request, jwtUtil)
            val data = service.update(user.convertToUser(), currentUser) ?: throw NotFoundException("data not found")
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @PutMapping("/changePass")
    fun changePassword(@RequestBody user: UserViewModel, request: HttpServletRequest): ServiceResponse<User> {
        return try {
            val currentUser = getCurrentUsername(request, jwtUtil)
            val data = service.changePassword(user.convertToUser(), user.repeatPass, user.oldPass, currentUser)
            ServiceResponse(listOf(data), HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

}