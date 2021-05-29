package study.kstock.stockapi.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import study.kstock.stockapi.domain.StockData
import study.kstock.stockapi.domain.StockMarket
import study.kstock.stockapi.domain.StockSymbol
import java.math.BigDecimal
import kotlin.jvm.Throws

@Service
class StockDataCrawlingService {

    @Throws
    @Cacheable(cacheNames = ["search"], key = "#symbolString", cacheManager = "redisCacheManager")
    fun searchStockData(symbolString: String): StockData {

        // TODO
        //  1. 미국 주식 거래소 목록 강제로 박음
        //  2. null 처리 깔끔하지 않은 부분 수정 필요
        val marketList = arrayOf("nyse", "amex", "nasd")
        var stockDataElement = Elements()
        var foundMarket = ""
        for (market in marketList) {
            val url = "https://finviz.com/screener.ashx?v=111&f=exch_${market}&t=${symbolString}"
            val document = Jsoup.connect(url).get().body()
            stockDataElement = document.getElementsByClass("table-dark-row-cp")
            if (stockDataElement.isNotEmpty()) {
                foundMarket = market
                break
            }
        }
        if (stockDataElement.isEmpty()) throw IllegalArgumentException()
        return getStockDataBy(stockDataElement[0], foundMarket)
    }

    fun getRecentStockDataListOf(market: String, start: Int): ArrayList<StockData>? {
        val url = "https://finviz.com/screener.ashx?v=111&f=exch_${market}&r=${start}"
        val stockDataList = ArrayList<StockData>()
        val document = Jsoup.connect(url).get().body()
        val stockDataElements = Elements()
        val parentsOne = document.getElementsByClass("table-light-row-cp")
        val parentsTwo = document.getElementsByClass("table-dark-row-cp")
        stockDataElements.addAll(parentsOne)
        stockDataElements.addAll(parentsTwo)

        if (stockDataElements.isEmpty()) return null

        for (stockDataElement in stockDataElements) {
            val stockData = getStockDataBy(stockDataElement, market)
            stockDataList.add(stockData)
        }

        return stockDataList
    }

    private fun getStockDataBy(stockDataElement: Element, market: String): StockData {
        val symbol = stockDataElement.child(1).text()
        val name = stockDataElement.child(2).text()
        val region = stockDataElement.child(5).text()
        val lastPrice = BigDecimal(stockDataElement.child(8).text())
        val percentChange = BigDecimal(stockDataElement.child(9).text().substringBefore('%'))
        val priceChange = lastPrice - lastPrice / (BigDecimal.ONE + (percentChange * BigDecimal(0.01)))
        return StockData(
            StockSymbol(
                symbol, name,
                StockMarket(market, region)
            ),
            lastPrice, priceChange, percentChange
        )
    }

    fun searchStocks(texts: Array<String>): ArrayList<StockData> {
        val result = ArrayList<StockData>()
        for (text in texts) {
            val searchedStockData = searchStockData(text)
            try {
                result.add(searchedStockData)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
        return result
    }
}