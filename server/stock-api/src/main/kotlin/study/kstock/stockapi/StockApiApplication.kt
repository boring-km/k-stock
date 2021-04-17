package study.kstock.stockapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class StockApiApplication

fun main(args: Array<String>) {
    runApplication<StockApiApplication>(*args)
}
