package study.kstock.stockcore.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class PingService {
    @KafkaListener(topics = ["stock-api"], containerFactory = "pingKafkaListenerContainerFactory")
    fun apiServiceListener(data: ConsumerRecord<String, Any>, ack: Acknowledgment) {
        try {
            val value = data.value()
            println("stock: $value")
            ack.acknowledge()
        } catch (e: Exception) {
            val msg = "시스템에 예상치 못한 문제가 발생했습니다"
            println("received ping message: $msg$e")
        }
    }

    @KafkaListener(topics = ["stock-external"], containerFactory = "pingKafkaListenerContainerFactory")
    fun externalServiceListener(data: ConsumerRecord<String, Any>, ack: Acknowledgment) {
        try {
            val value = data.value()
            println("received ping message: $value")
            ack.acknowledge()
        } catch (e: Exception) {
            val msg = "시스템에 예상치 못한 문제가 발생했습니다"
            println("received ping message: $msg$e")
        }
    }
}
