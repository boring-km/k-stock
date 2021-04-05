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

    fun getRecentPriceArrayOf(targetStockNameArray: Array<String>, region: String): Array<BigDecimal> {
        var stockArrayString = ""
        targetStockNameArray.forEach { name -> stockArrayString += name + "%2C" }
        stockArrayString = stockArrayString.substring(0, stockArrayString.length-3)

        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=$region&symbols=$stockArrayString"))
            .header("x-rapidapi-key", rapidAPIKey)
            .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build()
        val response: HttpResponse<String> =
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        val resultArray: JSONArray = JSONObject(response.body()).getJSONObject("quoteResponse").getJSONArray("result")
        val stockInfoArray: Array<BigDecimal> = Array(resultArray.length()) { BigDecimal(0) }
        for (i in 0 until resultArray.length()) {
            val stockInfo = resultArray.getJSONObject(i)
            if (stockInfo.has("regularMarketPrice")) {
                stockInfoArray[i] = resultArray.getJSONObject(i).getBigDecimal("regularMarketPrice")
            }
        }
        return stockInfoArray
    }


    fun getRecentStockDataArrayOf(startNum :Int, endNum :Int): ArrayList<String> {
        val url = "https://finviz.com/screener.ashx?v=111&f=exch_nasd,geo_usa&ft=4&r="
        val priceLsit = ArrayList<String>()
        var page = 1+(startNum-1)*20

        for(num in startNum..endNum) {
            val newUrl = url + page;
            val document = Jsoup.connect(newUrl).get().body()
            val parentsOne = document.getElementsByClass("table-light-row-cp")
            val parentsTwo = document.getElementsByClass("table-dark-row-cp")

            for(parent in parentsOne){
                val price:String = parent.child(8).text().toString()
                val change:String = parent.child(9).text().toString()
                val lastPrice: BigDecimal = BigDecimal(price)/(BigDecimal.ONE+(BigDecimal(change.substring(0,change.length-1))*BigDecimal(0.01)));

                priceLsit.add(parent.child(1).text()) //symbol
                priceLsit.add(price)
                priceLsit.add(change)
                priceLsit.add(lastPrice.toString())
            }

            for(parent in parentsTwo){
                val price:String = parent.child(8).text().toString()
                val change:String = parent.child(9).text().toString()
                val lastPrice: BigDecimal = BigDecimal(price)/(BigDecimal.ONE+(BigDecimal(change.substring(0,change.length-1))*BigDecimal(0.01)));

                priceLsit.add(parent.child(1).text()) //symbol
                priceLsit.add(price)
                priceLsit.add(change)
                priceLsit.add(lastPrice.toString())
            }
            page += 20
        }

        return priceLsit
    }

}
