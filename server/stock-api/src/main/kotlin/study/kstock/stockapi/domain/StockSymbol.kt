package study.kstock.stockapi.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
class StockSymbol @JsonCreator constructor(
    @JsonProperty("symbol") var symbol: String,
    @JsonProperty("name") var name: String,
    @JsonProperty("stockMarket") var stockMarket: StockMarket)