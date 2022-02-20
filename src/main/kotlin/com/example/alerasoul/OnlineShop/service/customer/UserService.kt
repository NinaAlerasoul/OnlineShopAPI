package com.example.alerasoul.OnlineShop.service.customer

import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.repository.customer.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var repository: UserRepository

    fun getById(id: Int): User? {
        val data = repository.findById(id)
        if (data.isEmpty) return null
        return data.get()
    }

    fun insert(data: User): User {
        return repository.save(data)
    }

    fun update(data: User): User? {
        val oldData = getById(data.id) ?: return null
        oldData.password = data.password
        return repository.save(oldData)
    }

    fun getByUsernameAndPass(userName: String, password: String): User? {
        return repository.findFirstByUsernameAndPassword(userName, password)
    }

    fun getTotalCount(): Long {
        return repository.count()
    }


}