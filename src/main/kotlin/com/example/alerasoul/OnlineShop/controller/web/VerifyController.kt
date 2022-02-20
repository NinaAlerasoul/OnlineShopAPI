package com.example.alerasoul.OnlineShop.controller.web

import com.example.alerasoul.OnlineShop.service.transaction.NextPayTransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("verify")
class VerifyController {

    @Autowired
    private lateinit var nextPayTransactionService: NextPayTransactionService

    @GetMapping("")
    fun index(
        @RequestParam trans_id: String,
        @RequestParam order_id: String,
        @RequestParam amount: Int,
        model:Model
    ): String {
        val verifyPayment = nextPayTransactionService.verifyPayment(trans_id, order_id, amount)
        model.addAttribute("code",verifyPayment.code)
        model.addAttribute("invoiceNumber",verifyPayment.invoiceNumber)
        model.addAttribute("referenceId",verifyPayment.referenceId)
        model.addAttribute("status",verifyPayment.status)

        return "verify.html"
    }
}