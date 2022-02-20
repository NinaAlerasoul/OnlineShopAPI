package com.example.alerasoul.OnlineShop


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class OnlineShopApplication

fun main(args: Array<String>) {
	runApplication<OnlineShopApplication>(*args)
}