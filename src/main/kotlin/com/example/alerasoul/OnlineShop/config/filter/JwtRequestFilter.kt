package com.example.ninaKotlinShopping.NinaKotlinShopping.config.filter

import com.example.alerasoul.OnlineShop.config.JwtTokenUtils
import com.example.alerasoul.OnlineShop.service.customer.UserService
import com.example.alerasoul.OnlineShop.viewmodel.UserViewModel
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : Filter {
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtils

    @Autowired
    private lateinit var userService: UserService

    private val excludeUrls = ArrayList<String>()

    private val excludeContainsUrls = ArrayList<String>()

    init {
        excludeUrls.add("/api2/user/login")
        excludeUrls.add("/api2/user/register")
        excludeUrls.add("/api2/trx/goToPayment")
        excludeUrls.add("/api2/trx/verify")
        excludeUrls.add("/verify")




        excludeContainsUrls.add("/api2/color")
        excludeContainsUrls.add("/api2/productCategory")
        excludeContainsUrls.add("/api2/product")
        excludeContainsUrls.add("/api2/blog")
        excludeContainsUrls.add("/api2/content")
        excludeContainsUrls.add("/api2/slider")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {
            val url = (request as HttpServletRequest).requestURI.lowercase(Locale.getDefault())
            if (excludeUrls.stream().anyMatch { x -> url == x.lowercase(Locale.getDefault()) } ||
                excludeContainsUrls.stream().anyMatch { x -> url.startsWith(x.lowercase(Locale.getDefault())) }) {
                chain!!.doFilter(request, response)
                return
            }
            val requestTokenHeader = request.getHeader("Authorization")
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer "))
                throw JwtException("request token header does not set")
            val token = requestTokenHeader.substring(7)
            val username: String = jwtTokenUtil.getUsernameFromToken(token)
            val userVM = UserViewModel(userService.getUserByUsername(username)!!)
            if (!jwtTokenUtil.validateToken(token, userVM))
                throw JwtException("invalid token")
            chain!!.doFilter(request, response)
        } catch (ex: JwtException) {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
        } catch (ex: ExpiredJwtException) {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_EXPECTATION_FAILED, ex.message)
        } catch (ex: Exception) {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.message)
        }
    }
}