package study.kstock.stockapi.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import study.kstock.stockapi.message.ApiRequest
import javax.annotation.Resource


@Service
class StockService {

    @Value(value = "\${stock-core-url}")
    private val coreURL: String = ""

    @Resource
    private lateinit var kafkaSendService: KafkaSendService

    @Resource
    private lateinit var webClient: WebClient

    fun saveStockIntoMyStock(symbol: String, userId: String): Boolean {
        // TODO userId는 세션에서 가져오도록 변경한다.
        val sendData = arrayOf(symbol, userId)
        return kafkaSendService.send(
            ApiRequest("saveStockIntoMyStock", sendData),
            object: ListenableFutureCallback<SendResult<String, Any>> {
                override fun onSuccess(result: SendResult<String, Any>?) {
                    val value = result!!.producerRecord.value()
                    println("Sent message=[$value] with offset=[${result.recordMetadata.offset()}]")
                }

                override fun onFailure(ex: Throwable) {
                    println("Unable to send message=[$sendData] due to : ${ex.message}")
                }
            }
        )
    }

    suspend fun getArrayOf20Stocks(market: String, start: Int): Array<Any> {
        return getResultToUseDefaultGetTo("$coreURL/stock/list/$market/$start")
    }

    suspend fun getMarketListBy(region: String): Array<Any> {
        return getResultToUseDefaultGetTo("$coreURL/market/list/$region")
    }

    private suspend fun getResultToUseDefaultGetTo(uri: String): Array<Any> {
        return webClient
            .mutate()
            .build()
            .get()
            .uri(uri)
            .retrieve()
            .awaitBody()
    }
}