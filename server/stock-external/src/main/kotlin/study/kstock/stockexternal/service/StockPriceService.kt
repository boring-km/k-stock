package study.kstock.stockexternal.service

import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import study.kstock.stockexternal.domain.StockData
import study.kstock.stockexternal.domain.StockMarket
import study.kstock.stockexternal.domain.StockSymbol
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


    fun getRecentStockDataListOf(market: String, region: String, startNum :Int, endNum :Int): ArrayList<StockData> {
        val url = "https://finviz.com/screener.ashx?v=111&f=exch_${market},geo_${region}&ft=4&r="
        val stockDataList = ArrayList<StockData>()
        var page = 1 + (startNum-1) * 20    // 한 페이지에 20개씩 있음

        for(num in startNum..endNum) {
            val newUrl = url + page
            val document = Jsoup.connect(newUrl).get().body()
            val stockDataElements = Elements()
            val parentsOne = document.getElementsByClass("table-light-row-cp")
            val parentsTwo = document.getElementsByClass("table-dark-row-cp")
            stockDataElements.addAll(parentsOne)
            stockDataElements.addAll(parentsTwo)

            for(parent in stockDataElements){
                val symbol = parent.child(1).text()
                val name = parent.child(3).text()
                val lastPrice = BigDecimal(parent.child(8).text())
                val percentChange = BigDecimal(parent.child(9).text().substringBefore('%'))
                val priceChange = lastPrice - lastPrice / (BigDecimal.ONE + (percentChange * BigDecimal(0.01)))
                val stockData = StockData(
                        StockSymbol(symbol, name,
                                StockMarket(market, region)),
                        lastPrice, priceChange, percentChange
                )
                stockDataList.add(stockData)
            }
            page += 20
        }

        return stockDataList
    }

    fun getRecentSymbolListOf(market: String): Array<StockSymbol?> {
        val request = HttpRequest.newBuilder()
                .uri(URI.create("https://twelve-data1.p.rapidapi.com/stocks?exchange=$market&format=json"))
                .header("x-rapidapi-key", rapidAPIKey)
                .header("x-rapidapi-host", "twelve-data1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build()
        val response: HttpResponse<String> = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())

        val resultArray: JSONArray = JSONObject(response.body()).getJSONArray("data")
        val stockSymbolArray = arrayOfNulls<StockSymbol>(resultArray.length())
        for (i in 0 until resultArray.length()) {
            val symbol = resultArray.getJSONObject(i).getString("symbol")
            val name = resultArray.getJSONObject(i).getString("name")
            val market = resultArray.getJSONObject(i).getString("exchange")
            val region = resultArray.getJSONObject(i).getString("country")
            val stockSymbol = StockSymbol(symbol, name, StockMarket(market, region))
            stockSymbolArray[i] = stockSymbol
        }
        return stockSymbolArray
    }

}
