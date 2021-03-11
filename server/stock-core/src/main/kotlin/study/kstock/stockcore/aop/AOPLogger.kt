package study.kstock.stockcore.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import study.kstock.stockcore.model.StockMarket

@Aspect
@Component
class AOPLogger {
    val logger: Logger = LoggerFactory.getLogger(AOPLogger::class.java)


    @Before("execution(* study.kstock.stockcore.model.Stock*.get*(..))")
    fun before(joinPoint: JoinPoint) {
        val args: Array<Any> = joinPoint.args
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        val method = signature.method
        var calledMethodString = ""
        for (i in 0 until method.parameterCount) {
            calledMethodString += method.parameters[i].name + "=" + args[i] + ", "
        }
        if (calledMethodString.isEmpty()) {
            calledMethodString = "없음  "
        }
        logger.info("[StockService]" +
                " calledMethod: ${method.name}(" +
                "${calledMethodString.substring(0, calledMethodString.length-2)})")
    }

    @AfterReturning(value= "execution(* study.kstock.stockcore.model.Stock*.get*(..))", returning = "stockMarketList")
    fun after(joinPoint: JoinPoint, stockMarketList: List<StockMarket>) {
        stockMarketList.forEach { stockMarket ->
            logger.info("거래소: ${stockMarket.marketName}")
        }
    }
}