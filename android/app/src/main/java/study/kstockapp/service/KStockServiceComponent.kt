package study.kstockapp.service

import dagger.Component

@Component
interface KStockServiceComponent {
    fun getServiceImpl() : KStockServiceImpl
}