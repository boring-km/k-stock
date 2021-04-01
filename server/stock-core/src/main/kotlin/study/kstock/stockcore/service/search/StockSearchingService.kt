package study.kstock.stockcore.service.search

import org.springframework.stereotype.Repository

@Repository
interface StockSearchingService<T> {
    fun search(vararg data: Any): MutableList<T>
}