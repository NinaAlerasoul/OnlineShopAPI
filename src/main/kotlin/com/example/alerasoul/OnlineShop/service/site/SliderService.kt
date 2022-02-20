package com.example.alerasoul.OnlineShop.service.site

import com.example.alerasoul.OnlineShop.model.site.Slider
import com.example.alerasoul.OnlineShop.repository.site.SliderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SliderService {

    @Autowired
    lateinit var repository: SliderRepository

    fun insert(data: Slider): Slider {
        return repository.save(data)
    }

    fun update(data: Slider): Slider? {
        val oldData = getById(data.id) ?: return null
        oldData.image = data.image
        oldData.title = data.title
        oldData.link = data.link
        oldData.subTitle = data.subTitle
        return repository.save(oldData)
    }

    fun delete(data: Slider): Boolean {
        repository.delete(data)
        return true
    }

    fun getById(id: Int): Slider? {
        val data = repository.findById(id)
        if (data.isEmpty)
            return null
        return data.get()
    }

    fun getAll(): List<Slider> {
        return repository.findAll()
    }

    fun getTotalCount(): Long {
        return repository.count()
    }

}
