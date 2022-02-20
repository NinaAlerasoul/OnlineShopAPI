package com.example.alerasoul.OnlineShop


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class NinaKotlinShoppingApplication

fun main(args: Array<String>) {
	runApplication<NinaKotlinShoppingApplication>(*args)
}