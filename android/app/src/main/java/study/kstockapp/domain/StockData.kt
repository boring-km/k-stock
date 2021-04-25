package study.kstockapp.domain

import java.io.Serializable
import java.math.BigDecimal

data class StockData(val stockSymbol: StockSymbol, val lastPrice: BigDecimal, val priceChange: BigDecimal, val percentChange: BigDecimal): Serializable