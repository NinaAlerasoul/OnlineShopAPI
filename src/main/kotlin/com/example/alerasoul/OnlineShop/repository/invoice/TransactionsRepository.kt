package com.example.alerasoul.OnlineShop.repository.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Transactions
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionsRepository : PagingAndSortingRepository<Transactions, Int> {

    fun findByTransId(transId: String): Transactions?

}