package study.kstock.stockapi.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*
import java.util.concurrent.TimeUnit

import io.netty.handler.timeout.WriteTimeoutHandler

import io.netty.handler.timeout.ReadTimeoutHandler

import io.netty.channel.ChannelOption
import org.springframework.http.HttpMethod
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.awaitBody
import reactor.netty.http.client.HttpClient
import java.time.Duration


@Service
class StockService {

    @Value("stock-core-url")
    private lateinit var coreURL: String

    suspend fun getStockMarketList(region: String): Array<String> {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }
        val client = WebClient.builder()
            .baseUrl(coreURL)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", coreURL))
            .build()

        val uriSpec: WebClient.UriSpec<*> = client.get()
        return uriSpec.uri("/market/list").retrieve().awaitBody<Array<String>>()

    }
}