package com.example.ninaKotlinShopping.NinaKotlinShopping.service.site

import com.example.alerasoul.OnlineShop.model.site.Content
import com.example.alerasoul.OnlineShop.repository.site.ContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentService {

    @Autowired
    lateinit var repository: ContentRepository

    fun getAll(): List<Content> {
        return repository.findAll()
    }

    fun getByTitle(title: String): Content? {
        return repository.findFirstByTitle(title)
    }

    fun getById(id: Int): Content? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}