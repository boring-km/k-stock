package study.kstock.stockcore.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash
class StockCache(
    @Id
    var time: String,
    var data: StockMarket
)