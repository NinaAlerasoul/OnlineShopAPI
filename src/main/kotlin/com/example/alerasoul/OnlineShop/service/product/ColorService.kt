package com.example.alerasoul.OnlineShop.service.product

import com.example.alerasoul.OnlineShop.model.product.Color
import com.example.alerasoul.OnlineShop.repository.product.ColorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ColorService {

    @Autowired
    lateinit var repository: ColorRepository

    fun getById(id: Int): Color? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getAll(): List<Color> {
        return repository.findAll()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}