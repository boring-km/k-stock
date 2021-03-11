package study.kstock.stockapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kstock.stockapi.domain.Pong
import java.lang.Exception


@Component
class PingService {

    @Autowired
    private lateinit var pingKafkaTemplate: KafkaTemplate<String, Any>

    @Throws(Exception::class)
    fun pingAndPong(ping: Any): Pong {
        val pingTopicName = "stock-api"
        val future = pingKafkaTemplate.send(pingTopicName, ping)
        future.addCallback(object : ListenableFutureCallback<SendResult<String?, Any?>?> {
            override fun onSuccess(result: SendResult<String?, Any?>?) {
                val g = result!!.producerRecord.value()
                println("Sent message=[" + g.toString() + "] with offset=[" + result.recordMetadata.offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                println("Unable to send message=[" + ping.toString() + "] due to : " + ex.message)
            }
        })
        return Pong("Pong(name)", "Pong(Message)")
    }
}