package com.example.alerasoul.OnlineShop.service.customer

import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.repository.customer.UserRepository
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
        if (data.isEmpty) return null
        return data.get()
    }

    fun getUserByUsername(username: String): User? {
        val data = repository.findUserByUsername(username)
        return data
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
        customerService.insert(data.customer!!)
        return repository.save(data)
    }

    fun update(data: User): User? {
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
        return repository.findFirstByUsernameAndPassword(userName, password)
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

    fun changePassword(data: User, repeatPass: String, oldPass: String): User {
        if (data.username.isEmpty())
            throw Exception("username is empty")
        if (oldPass.isEmpty())
            throw Exception("old pass is empty")
        if (data.password.isEmpty() || repeatPass.isEmpty())
            throw Exception("enter password and repeat")
        if (data.password != repeatPass)
            throw Exception("password and repeat are not match ")
        val oldData = getUserByUsername(data.username) ?: throw Exception("data not found")
        if (oldPass != oldData.password)
            throw Exception("current pass is false")
        oldData.password = data.password
        val savedData = repository.save(oldData)
        savedData.password = ""
        return savedData
    }

}