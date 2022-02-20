package com.example.alerasoul.OnlineShop.repository.customer


import com.example.alerasoul.OnlineShop.model.customer.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<User, Int> {

    fun findFirstByUsernameAndPassword(username: String, password: String): User?

    fun findUserByUsername(username: String): User?

}