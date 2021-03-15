package study.kstock.stockexternal.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Aspect
@Component
class AOPLogger {

    private val logger: Logger = LoggerFactory.getLogger(AOPLogger::class.java)

    @AfterReturning(value= "execution(* study.kstock.stockexternal.service.*.getRecentPrice*(..))", returning = "priceArray")
    fun logAfterGetStockPriceArray(joinPoint: JoinPoint, priceArray: Array<BigDecimal>) {
        logger.info("호출한 주식들의 현재 가격 Array: ${priceArray.contentToString()}")
    }
}