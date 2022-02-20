package com.example.alerasoul.OnlineShop.service.customer

import com.example.alerasoul.OnlineShop.model.customer.Customer
import com.example.alerasoul.OnlineShop.repository.customer.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Autowired
    lateinit var repository: CustomerRepository

    fun update(data: Customer): Customer? {
        val oldData = getById(data.id) ?: return null
        oldData.firstName = data.firstName
        oldData.lastName = data.lastName
        oldData.address = data.address
        oldData.phone = data.phone
        oldData.postalCode = data.postalCode
        return repository.save(oldData)
    }

    fun insert(data: Customer): Customer {
        if (data.id<=0)
            throw Exception("invalid customer id")
        if (data.firstName.isEmpty())
            throw Exception("firstname is empty")
        if (data.lastName.isEmpty())
            throw Exception("lastName is empty")
        if (data.phone.isEmpty())
            throw Exception("phone is empty")
        if (data.postalCode.isEmpty())
            throw Exception("postalCode is empty")
        return repository.save(data)
    }

    fun getById(id: Int): Customer? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}