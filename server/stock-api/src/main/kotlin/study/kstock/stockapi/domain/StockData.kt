package study.kstock.stockapi.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
data class StockData @JsonCreator constructor(
    @JsonProperty("stockSymbol") val stockSymbol: StockSymbol,
    @JsonProperty("lastPrice") val lastPrice: BigDecimal,
    @JsonProperty("priceChange") val priceChange: BigDecimal,
    @JsonProperty("percentChange") val percentChange: BigDecimal)