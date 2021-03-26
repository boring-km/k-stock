package study.kstock.stockcore.service.search

import org.springframework.stereotype.Repository

@Repository
interface SearchStockService<T> {
    fun search(vararg data: Any): MutableList<T>
}