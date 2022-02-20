package com.example.alerasoul.OnlineShop.service.customer

import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.repository.customer.UserRepository
import com.example.alerasoul.OnlineShop.util.SecurityUtil
import com.example.alerasoul.OnlineShop.util.SecurityUtil.Companion.encryptSHA256
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var repository: UserRepository

    @Autowired
    lateinit var customerService: CustomerService


    fun getById(id: Int): User? {
        val data = repository.findById(id)
        return data.get()
    }

    fun getUserByUsername(username: String): User? {
        if (username.isEmpty())
            throw Exception("username is empty")
        return repository.findUserByUsername(username)
    }

    fun insert(data: User): User {
        if (data.id <= 0)
            throw Exception("invalid user id")
        if (data.customer == null)
            throw Exception("data is empty")
        if (data.username.isEmpty())
            throw Exception("username is empty")
        if (data.password.isEmpty())
            throw Exception("password is empty")
        val duplicate = getUserByUsername(data.username)
        if (duplicate != null)
            throw Exception("this username is already exists")
        val hashPass = SecurityUtil.encryptSHA256(data.password)
        data.password = hashPass
        customerService.insert(data.customer!!)
        val savedData = repository.save(data)
        savedData.password = ""
        return savedData
    }

    fun update(data: User, currentUser: String): User? {
        val user = repository.findUserByUsername(currentUser)
        if (user == null || data.id != user.id)
            throw Exception("you don't have permission")
        if (data.customer!!.firstName.isEmpty())
            throw Exception("firstname is empty")
        if (data.customer!!.lastName.isEmpty())
            throw Exception("lastName is empty")
        if (data.customer!!.phone.isEmpty())
            throw Exception("phone is empty")
        val oldCustomer = customerService.getById(data.customer!!.id) ?: return null
        val oldData = getById(data.id) ?: throw Exception("data not found")
        oldData.customer!!.firstName = data.customer!!.firstName
        oldData.customer!!.lastName = data.customer!!.lastName
        oldData.customer!!.phone = data.customer!!.phone
        if (data.customer!!.postalCode.isNotEmpty())
            oldData.customer!!.postalCode = data.customer!!.postalCode
        if (data.customer!!.address.isNotEmpty())
            oldData.customer!!.address = data.customer!!.address
        customerService.update(oldCustomer)
        data.password = ""
        return data
    }

    fun getByUsernameAndPass(userName: String, password: String): User? {
        if (userName.isEmpty() && password.isEmpty())
            throw Exception("username and password are empty")
        if (userName.isEmpty())
            throw Exception("username is empty")
        if (password.isEmpty())
            throw Exception("password is empty")
        val hashPass = SecurityUtil.encryptSHA256(password)
        return repository.findFirstByUsernameAndPassword(userName, hashPass)
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

    fun changePassword(data: User, repeatPass: String, oldPass: String, currentUser: String): User {
        val user = repository.findUserByUsername(currentUser)
        if (user == null || data.id != user.id)
            throw Exception("you don't have permission")
        if (data.username.isEmpty())
            throw Exception("username is empty")
        if (oldPass.isEmpty())
            throw Exception("old pass is empty")
        if (data.password.isEmpty() || repeatPass.isEmpty())
            throw Exception("enter password and repeat")
        if (data.password != repeatPass)
            throw Exception("password and repeat are not match ")
        if (SecurityUtil.encryptSHA256(oldPass) != user.password)
            throw Exception("current pass is false")
        val hashPass = SecurityUtil.encryptSHA256(data.password)
        user.password = hashPass
        val savedData = repository.save(user)
        savedData.password = ""
        return savedData
    }

}