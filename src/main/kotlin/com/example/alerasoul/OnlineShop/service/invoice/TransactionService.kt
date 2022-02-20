package com.example.alerasoul.OnlineShop.service.invoice

import com.example.alerasoul.OnlineShop.model.invoice.Transactions
import com.example.alerasoul.OnlineShop.repository.invoice.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService {

    @Autowired
    private lateinit var repository: TransactionsRepository

    fun insert(data: Transactions): Transactions {
        return repository.save(data)
    }

    fun update(data: Transactions): Transactions? {
        val oldData = getById(data.id) ?: return null
        oldData.refId = data.refId
        oldData.code = data.code
        oldData.custom = data.custom
        return repository.save(oldData)
    }

    fun getById(id: Int): Transactions? {
        val data = repository.findById(id)
        if (data.isEmpty) return null
        return data.get()
    }

    fun getByTransId(transId: String): Transactions? {
        return repository.findByTransId(transId) ?: return null
    }

}