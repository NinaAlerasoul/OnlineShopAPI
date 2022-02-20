package com.example.alerasoul.OnlineShop.repository.site

import com.example.alerasoul.OnlineShop.model.site.Blog
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogRepository : PagingAndSortingRepository<Blog, Int> {
    override fun findAll(): List<Blog>
}