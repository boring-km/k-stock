package study.kstock.stockapi.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
class StockMarket @JsonCreator constructor(
    @JsonProperty("market") var market: String,
    @JsonProperty("region") var region: String
)