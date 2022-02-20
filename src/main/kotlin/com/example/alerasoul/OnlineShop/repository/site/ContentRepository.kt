package com.example.alerasoul.OnlineShop.repository.site

import com.example.alerasoul.OnlineShop.model.site.Content
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentRepository : PagingAndSortingRepository<Content, Int> {
    override fun findAll(): List<Content>
    fun findFirstByTitle(title: String): Content?
}