package study.kstock.stockexternal.service

import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class StockPriceService {

    @Value("\${rapid-api-key}")
    private lateinit var rapidAPIKey: String

    fun getRecentPriceOf(stockSymbol: String): Double {
        val url = "https://finance.yahoo.com/quote/$stockSymbol"
        val document = Jsoup.connect(url).get().body()
        val parents = document.tagName("div").getElementsByAttributeValue("data-reactid", "31")
        for (parent in parents) {
            val found: Element? = parent.children().find { child -> child.tagName("span").attr("data-reactid").equals("32") }
            if (found != null) {
                return found.text().toDouble()
            }
        }
        throw IllegalArgumentException("Yahoo Finance에 없는 주식입니다")
    }

    fun getRecentPriceArrayOf(targetStockNameArray: Array<String>): Array<BigDecimal> {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=AMD%2CIBM%2CAAPL"))
            .header("x-rapidapi-key", rapidAPIKey)
            .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build()
        val response: HttpResponse<String> =
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        val result: String = response.body()
        val array: JSONArray = JSONObject(result).getJSONObject("quoteResponse").getJSONArray("result")
        val priceArray: Array<BigDecimal> = Array(array.length()) { BigDecimal(0) }
        for (i in 0 until array.length()) {
            priceArray[i] = array.getJSONObject(i).getBigDecimal("regularMarketPrice")
        }
        return priceArray
    }
}
