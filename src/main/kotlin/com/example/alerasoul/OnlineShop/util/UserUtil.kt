package com.example.alerasoul.OnlineShop.util

import com.example.alerasoul.OnlineShop.util.exception.JwtTokenException
import com.example.alerasoul.OnlineShop.config.JwtTokenUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

class UserUtil {
    companion object {
        fun getCurrentUsername(request: HttpServletRequest, jwtUtil: JwtTokenUtils): String {
            val header = request.getHeader("Authorization")
            if (header == null || !header.lowercase(Locale.getDefault()).startsWith("bearer"))
                throw JwtTokenException("please set bearer token")
            val token = header.substring(7)
            return jwtUtil.getUsernameFromToken(token)
        }
    }
} 