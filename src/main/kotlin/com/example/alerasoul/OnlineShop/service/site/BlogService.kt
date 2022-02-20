package com.example.alerasoul.OnlineShop.service.site

import com.example.alerasoul.OnlineShop.model.site.Blog
import com.example.alerasoul.OnlineShop.repository.site.BlogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BlogService {

    @Autowired
    lateinit var repository: BlogRepository

    fun getById(id: Int): Blog? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getAll(): List<Blog> {
        return repository.findAll()
    }

    fun getAll(pageIndex: Int, pageSize: Int): List<Blog> {
        val pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id"))
        return repository.findAll(pageRequest).toList()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}