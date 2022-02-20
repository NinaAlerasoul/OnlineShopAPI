package com.example.alerasoul.OnlineShop.viewmodel;

import com.example.alerasoul.OnlineShop.model.customer.Customer
import com.example.alerasoul.OnlineShop.model.customer.User

data class UserViewModel(
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
    var repeatPass: String = "",
    var oldPass: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var phone: String = "",
    var postalCode: String = "",
    var customerId: Int = 0
) {
    fun convertToUser(): User {
        return User(id, username, password, convertToCustomer())
    }

    fun convertToCustomer(): Customer {
        return Customer(customerId, firstName, lastName, address, phone, postalCode)
    }
}