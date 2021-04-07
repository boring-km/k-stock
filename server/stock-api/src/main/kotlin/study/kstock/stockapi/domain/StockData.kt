package study.kstock.stockapi.domain

import java.math.BigDecimal

data class StockData(val stockSymbol: StockSymbol, val lastPrice: BigDecimal, val priceChange: BigDecimal, val percentChange: BigDecimal)