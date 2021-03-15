package study.kstock.stockexternal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockExternalApplication

fun main(args: Array<String>) {
    runApplication<StockExternalApplication>(*args)
}
