package study.kstock.stockcore.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ReceiveService {

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(ReceiveService::class.java)

    @KafkaListener(topics = ["stock-api", "stock-external"], containerFactory = "receiveKafkaListenerContainerFactory")
    fun serviceListener(data: ConsumerRecord<String, Any>, ack: Acknowledgment) {
        @Suppress("UNCHECKED_CAST")
        try {
            val value: LinkedHashMap<String, Any> = data.value() as LinkedHashMap<String, Any>
            // TODO: 처리 업무가 필요한 service의 메서드 호출
            //  1. 사용자 처리 요청 (stock-api)
            //  2. 주식 데이터 저장 요청 (stock-external)
            ack.acknowledge()
        } catch (e: Exception) {
            val msg = "시스템에 예상치 못한 문제가 발생했습니다"
            logger.error("received ping message: $msg$e")
        }
    }
}
