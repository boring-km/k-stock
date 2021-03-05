package study.kstock.stockcore.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import study.kstock.stockcore.model.StockCache
import study.kstock.stockcore.model.StockCacheRepository
import study.kstock.stockcore.model.StockMarket
import java.net.CacheResponse
import java.time.LocalTime
import javax.annotation.Resource

@Aspect
@Component
class AOPLogger {
    val logger: Logger = LoggerFactory.getLogger(AOPLogger::class.java)

//    @Resource
    lateinit var stockCacheRepository: StockCacheRepository

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

//    @AfterReturning(value= "execution(* study.kstock.stockcore.model.Stock*.get*(..))", returning = "stockMarket")
    fun after(joinPoint: JoinPoint, stockMarket: StockMarket) {
        val now = LocalTime.now().toString()
        stockCacheRepository.save(StockCache(now, stockMarket))
    }
}