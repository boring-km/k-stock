package study.kstock.stockcore.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockMarketRepository: JpaRepository<StockMarket, Int> {
    fun findByRegion(country: String): MutableList<StockMarket>
}

@Repository
interface StockDataRepository: JpaRepository<StockData, Int> {

}

@Repository
interface StockSymbolRepository: JpaRepository<StockSymbol, Int>

@Repository
interface StockDataViewRepository: JpaRepository<StockDataView, Int>