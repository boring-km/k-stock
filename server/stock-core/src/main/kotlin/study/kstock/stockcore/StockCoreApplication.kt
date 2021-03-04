package study.kstock.stockcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class StockCoreApplication

fun main(args: Array<String>) {
    runApplication<StockCoreApplication>(*args)
}
