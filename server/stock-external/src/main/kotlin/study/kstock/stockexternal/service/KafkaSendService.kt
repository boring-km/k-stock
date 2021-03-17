package study.kstock.stockexternal.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kstock.stockexternal.message.ApiRequest
import java.lang.Exception

@Component
class KafkaSendService {
    @Autowired
    private lateinit var sendKafkaTemplate: KafkaTemplate<String, Any>
    val logger: Logger = LoggerFactory.getLogger(KafkaSendService::class.java)

    // TODO 카프카 publish 메서드를 주기적으로 사용하기 위한 배치 서비스 개발이 필요함
    fun send(data: ApiRequest, callback: ListenableFutureCallback<SendResult<String, Any>>): Boolean {
        try {
            val topic = "stock-external"
            sendKafkaTemplate.send(topic, data).addCallback(callback)
        } catch (e: Exception) {
            logger.error("kafka publish error: ${e.message}")
            return false
        }
        return true
    }
}