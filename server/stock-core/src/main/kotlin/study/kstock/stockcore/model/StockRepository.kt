package study.kstock.stockcore.model

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StockMarketRepository: JpaRepository<StockMarket, Int> {
    fun findByRegion(country: String): MutableList<StockMarket>
}

@Repository
interface StockDataRepository: JpaRepository<StockData, Int> {
    @Query("select data from StockData data where data.stockSymbol.stockMarket.marketName = :name")
    fun getArrayOf20Stocks(@Param("name") name: String, pageable: Pageable): MutableList<StockData>
}

@Repository
interface StockSymbolRepository: JpaRepository<StockSymbol, Int>
