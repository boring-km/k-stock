package study.kstock.stockapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kstock.stockapi.domain.Ping
import study.kstock.stockapi.domain.Pong
import java.lang.Exception


@Component
class PingService {
    @Autowired
    private val pingKafkaTemplate: KafkaTemplate<String, Ping>? = null

    @Value(value = "\${ping.topic.name}")
    private val pingTopicName: String? = null

    @Throws(Exception::class)
    fun pingAndPong(ping: Ping): Pong {
        val future = pingKafkaTemplate!!.send(
            pingTopicName!!, ping
        )
        future.addCallback(object : ListenableFutureCallback<SendResult<String?, Ping?>?> {
            override fun onSuccess(result: SendResult<String?, Ping?>?) {
                val g = result!!.producerRecord.value()
                println("Sent message=[" + g.toString() + "] with offset=[" + result.recordMetadata.offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                println("Unable to send message=[" + ping.toString() + "] due to : " + ex.message)
            }
        })
        return Pong("Son", "Hello~!")
    }
}