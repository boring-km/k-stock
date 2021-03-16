package study.kstock.stockapi.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kstock.stockapi.message.ApiRequest
import java.lang.Exception

@Component
class KafkaSendService {
    @Autowired
    private lateinit var sendKafkaTemplate: KafkaTemplate<String, Any>
    val logger: Logger = LoggerFactory.getLogger(KafkaSendService::class.java)

    fun send(data: ApiRequest, callback: ListenableFutureCallback<SendResult<String, Any>>): Boolean {
        try {
            val topic = "stock-api"
            sendKafkaTemplate.send(topic, data).addCallback(callback)
        } catch (e: Exception) {
            logger.error("kafka publish error: ${e.message}")
            return false
        }
        return true
    }
}