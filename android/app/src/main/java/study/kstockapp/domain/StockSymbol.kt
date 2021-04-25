package study.kstockapp.domain

import java.io.Serializable

data class StockSymbol(var symbol: String, var name: String, var stockMarket: StockMarket): Serializable