package com.example.alerasoul.OnlineShop.service.transaction

import com.example.alerasoul.OnlineShop.config.payment.NextPayConfig
import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.example.alerasoul.OnlineShop.model.invoice.Transactions
import com.example.alerasoul.OnlineShop.model.product.Product
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.TokenRequest
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.TokenResponse
import com.example.alerasoul.OnlineShop.service.customer.UserService
import com.example.alerasoul.OnlineShop.service.invoice.InvoiceService
import com.example.alerasoul.OnlineShop.service.invoice.TransactionService
import com.example.alerasoul.OnlineShop.service.product.ProductService
import com.example.alerasoul.OnlineShop.util.payment.NextPayStatusCodeUtil
import com.example.alerasoul.OnlineShop.viewmodel.PaymentViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NextPayTransactionService {
    @Autowired
    lateinit var configuration: NextPayConfig

    @Autowired
    lateinit var transactionsService: TransactionService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var invoiceService: InvoiceService

    @Autowired
    lateinit var productService: ProductService


    fun getPaymentUri(data: PaymentViewModel): String {

        checkValidation(data)

        var user: User? = registerUser(data)
        if (user == null) {
            user = userService.getById(data.user.id)
            userService.update(data.user.convertToUser(), user!!.username)
        }
        var currentUser = user.username

        val invoice = createInvoice(user, data, currentUser)

        val productList = getProductsList(data)

        var amount: Long = getTotalAmount(data, productList)

        val response = preparePaymentUri(data, amount, invoice)

        val trx = Transactions(
            transId = response.trans_id,
            code = response.code,
            amount = amount.toInt(),
            customerPhone = data.user.phone,
            custom = NextPayStatusCodeUtil.getMessage(response.code)
        )
        transactionsService.insert(trx)
        if (trx.code != -1)
            throw Exception(NextPayStatusCodeUtil.getMessage(response.code))
        return "${configuration.paymentUri}${response.trans_id}"
    }

    private fun getTotalAmount(
        data: PaymentViewModel,
        productList: List<Product>
    ): Long {
        var amount: Long = 0
        data.items.forEach { item ->
            var product = productList.first { x -> x.id == item.product!!.id }
            amount += product.price * item.quantity
        }
        return amount
    }

    private fun getProductsList(data: PaymentViewModel): List<Product> {
        var idList = ArrayList<Int>()
        data.items.forEach {
            idList.add(it.product!!.id)
        }
        return productService.getAllByIdList(idList)
    }

    private fun createInvoice(
        user: User?,
        data: PaymentViewModel,
        currentUser: String
    ): Invoice {
        var invoice = Invoice(
            user = user,
            invoiceItems = data.items
        )
        //ToDo
        invoiceService.insert(invoice, currentUser)
        return invoice
    }

    private fun registerUser(data: PaymentViewModel): User? {
        if (data.user.id <= 0) {
            userService.insert(data.user.convertToUser())
        }
        return null
    }

    private fun checkValidation(data: PaymentViewModel) {
        if (data?.user == null || data?.items == null)
            throw Exception("Invalid input data")
    }

    fun preparePaymentUri(data: PaymentViewModel, amount: Long, invoice: Invoice): TokenResponse {


        val mapper = jacksonObjectMapper()
        val url = configuration.tokenEndPoint
        val restClient = RestTemplate()
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = TokenRequest(
            api_key = configuration.apiKey,
            order_id = invoice.id.toString(),
            amount = amount.toInt(),
            customer_phone = data.user.phone,
            callback_uri = configuration.callbackUri,
        )

        val resp = restClient.postForEntity(url, HttpEntity(request, headers), String::class.java)
        if (resp.statusCode.is2xxSuccessful) {
            return mapper.readValue(resp.body!!)
        } else {
            throw IllegalStateException()
        }
    }

} 