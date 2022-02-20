package com.example.alerasoul.OnlineShop.controller.transaction

import com.example.alerasoul.OnlineShop.util.ServiceResponse
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.viewmodel.PaymentViewModel
import com.example.alerasoul.OnlineShop.viewmodel.VerifyResponseViewModel
import com.example.alerasoul.OnlineShop.service.transaction.NextPayTransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }

    @GetMapping("/verify")
    fun verify(
        @RequestParam trans_id: String,
        @RequestParam order_id: String,
        @RequestParam amount: Int
    ): ServiceResponse<VerifyResponseViewModel> {
        return try {
            ServiceResponse(listOf(nextPayTransactionService.verifyPayment(trans_id, order_id, amount)), HttpStatus.OK)
        } catch (e: Exception) {
            ServiceResponse(status = HttpStatus.INTERNAL_SERVER_ERROR, message = e.message!!)
        }
    }


}