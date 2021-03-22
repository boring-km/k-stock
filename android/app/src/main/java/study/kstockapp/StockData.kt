package study.kstockapp

import java.io.Serializable

data class StockData (
    val stockName: String,
    val stockMarket: String,
    val currentPrice: Double,
    val priceChange: Double,
    val percentChange: Double
) : Serializable