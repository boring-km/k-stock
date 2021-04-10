package study.kstock.stockcore.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import study.kstock.stockcore.message.KafkaDataMap
import javax.annotation.Resource

@Service
class KafkaReceiveService {

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(KafkaReceiveService::class.java)

    @Resource
    private lateinit var commandMappingService: CommandMappingService

    @KafkaListener(topics = ["stock-api", "stock-external"], containerFactory = "receiveKafkaListenerContainerFactory")
    fun serviceListener(data: ConsumerRecord<String, Any>, ack: Acknowledgment) {
        @Suppress("UNCHECKED_CAST")
        try {
            val value = KafkaDataMap(data.value() as LinkedHashMap<String, Any>)
            // 처리 업무가 필요한 service의 공통 execute 메서드 호출
            //  1. 사용자 처리 요청 (stock-api)
            //  2. 주식 데이터 저장 요청 (stock-external)
            execute(kafkaDataMap = value)
            ack.acknowledge()
        } catch (e: Exception) {
            logger.error("카프카 메시지 수신 에러: $e")
        }
    }

    private fun execute(kafkaDataMap: KafkaDataMap) {
        val command = kafkaDataMap.command()
        val data = kafkaDataMap.data()
        try {
            commandMappingService.execute(command, data)
        } catch (error: Exception) {
            logger.error("Kafka 명령 수행 중 에러 발생: ${error.message}")
        }
    }
}

