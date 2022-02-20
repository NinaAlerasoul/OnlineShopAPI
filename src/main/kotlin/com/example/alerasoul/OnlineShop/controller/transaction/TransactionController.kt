package com.example.alerasoul.OnlineShop.controller.transaction

import com.example.alerasoul.OnlineShop.service.transaction.NextPayTransactionService
import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.viewmodel.PaymentViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api2/trx")
class TransactionController {
    @Autowired
    lateinit var nextPayTransactionService: NextPayTransactionService



    @PostMapping("/goToPayment")
    fun goToPayment(@RequestBody paymentViewModel: PaymentViewModel):ServiceResponse<String>{
        return try {
            ServiceResponse(listOf(nextPayTransactionService.getPaymentUri(paymentViewModel)), status = HttpStatus.OK)
        } catch (e: NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

} 