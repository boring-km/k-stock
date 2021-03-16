package study.kstock.stockcore.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StockMarketRepository: JpaRepository<StockMarket, Int> {
    fun findByMarketName(name: String): Optional<StockMarket>
}

@Repository
interface StockDataRepository: JpaRepository<StockData, Int>

@Repository
interface StockSymbolRepository: JpaRepository<StockSymbol, Int>