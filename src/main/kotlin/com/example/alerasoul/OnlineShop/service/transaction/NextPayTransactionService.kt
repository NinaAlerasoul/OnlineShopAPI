package com.example.alerasoul.OnlineShop.service.transaction

import com.example.alerasoul.OnlineShop.config.payment.NextPayConfig
import com.example.alerasoul.OnlineShop.model.customer.User
import com.example.alerasoul.OnlineShop.model.invoice.Invoice
import com.example.alerasoul.OnlineShop.model.invoice.Transactions
import com.example.alerasoul.OnlineShop.model.product.Product
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.TokenRequest
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.TokenResponse
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.VerifyRequest
import com.example.alerasoul.OnlineShop.model.transaction.nextpay.VerifyResponse
import com.example.alerasoul.OnlineShop.service.customer.UserService
import com.example.alerasoul.OnlineShop.service.invoice.InvoiceService
import com.example.alerasoul.OnlineShop.service.invoice.TransactionService
import com.example.alerasoul.OnlineShop.service.product.ProductService
import com.example.alerasoul.OnlineShop.util.exception.NotFoundException
import com.example.alerasoul.OnlineShop.util.payment.NextPayStatusCodeUtil
import com.example.alerasoul.OnlineShop.viewmodel.PaymentViewModel
import com.example.alerasoul.OnlineShop.viewmodel.VerifyResponseViewModel
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
    private lateinit var configuration: NextPayConfig

    @Autowired
    private lateinit var trxService: TransactionService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var invoiceService: InvoiceService

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var transactionService: TransactionService

    fun getPaymentUri(data: PaymentViewModel): String {

        checkValidation(data)

        var user: User? = null
//        var user: User? = registerUser(data)
        if (user == null) {
            user = userService.getUserByUsername(data.user.username)
//            userService.update(data.user.convertToUser(), user!!.username)
        }

        val currentUser = user!!.username

        val invoice = createInvoice(user, data, currentUser)

        val productList = getProducts(data)

        var amount: Long = getTotalAmount(data, productList)

        val response = preparePaymentUrl(data, amount, invoice)
        val trx = Transactions(
            transId = response.trans_id,
            code = response.code,
            amount = amount.toInt(),
            customerPhone = data.user.phone,
            invoice = invoice,
            custom = NextPayStatusCodeUtil.getMessage(response.code)
        )
        trxService.insert(trx)
        if (response.code != -1)
            throw Exception(NextPayStatusCodeUtil.getMessage(response.code))
        return "${configuration.paymentUri}${response.trans_id}"
    }

    fun verifyPayment(trans_id: String, order_id: String, amount: Int): VerifyResponseViewModel {
        val trx = transactionService.getByTransId(trans_id) ?: throw NotFoundException("Transaction not found!")

        val response = doVerifyPayment(trans_id, trx)

        trx.code = response.code!!
        trx.custom = NextPayStatusCodeUtil.getMessage(response.code!!)

        if (trx.code == 0) {
            trx.refId = response.Shaparak_Ref_Id!!
        }

        transactionService.update(trx)

        return VerifyResponseViewModel(
            NextPayStatusCodeUtil.getMessage(response.code!!),
            trx.refId,
            trx.invoice!!.id,
            trx.code
        )
    }

    private fun doVerifyPayment(trans_id: String, trx: Transactions): VerifyResponse {
        val mapper = jacksonObjectMapper()
        val url = configuration.verifyUri
        val restClient = RestTemplate()
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val request = VerifyRequest(configuration.apiKey, trans_id, trx.amount)

        val resp = restClient.postForEntity(url, HttpEntity(request, headers), String::class.java)
        if (resp.statusCode.is2xxSuccessful) {
            return mapper.readValue(resp.body!!)
        } else {
            throw IllegalStateException()
        }
    }

    //region helper methods
    private fun getTotalAmount(
        data: PaymentViewModel,
        productList: List<Product>
    ): Long {
        var amount: Long = 0
        data.items.forEach { item ->
            val product = productList.first { x -> x.id == item.product!!.id }
            amount += product.price * item.quantity
        }
        return amount
    }

    private fun getProducts(data: PaymentViewModel): List<Product> {
        val idList = ArrayList<Int>()
        data.items.forEach {
            idList.add(it.product!!.id)
        }
        val productList = productService.getAllByIdList(idList)
        return productList
    }

    private fun createInvoice(
        user: User?,
        data: PaymentViewModel,
        currentUser: String
    ): Invoice {
        val invoice = Invoice(
            user = user,
            invoiceItems = data.items
        )
        invoiceService.insert(invoice, currentUser)
        return invoice
    }

    private fun registerUser(data: PaymentViewModel): User? {
        if (data.user.id <= 0) {
            return userService.insert(data.user.convertToUser())
        }
        return null
    }

    private fun checkValidation(data: PaymentViewModel) {
        if (data?.user == null || data?.items == null)
            throw Exception("Invalid input data")
    }

    private fun preparePaymentUrl(data: PaymentViewModel, amount: Long, invoice: Invoice): TokenResponse {
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
    //endregion
}