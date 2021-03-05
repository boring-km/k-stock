package study.kstock.stockcore.model

import org.springframework.data.repository.CrudRepository

interface StockCacheRepository: CrudRepository<StockCache, String>