package study.kstock.stockexternal.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kstock.stockexternal.message.ApiRequest

@SpringBootTest
internal class KafkaSendServiceTest {

    @Autowired
    private lateinit var kafkaSendService: KafkaSendService
    private val logger: Logger = LoggerFactory.getLogger(KafkaSendServiceTest::class.java)

    @DisplayName("Kafka 서비스 send(publish) 테스트가 성공하면 true를 반환한다")
    @Test
    fun send() {
        val result = kafkaSendService.send(
            ApiRequest("testCommand", "This is Test Data"),
            object: ListenableFutureCallback<SendResult<String, Any>> {
                override fun onSuccess(result: SendResult<String, Any>?) {
                    logger.info("Kafka send test is Success, result:$result")
                }

                override fun onFailure(ex: Throwable) {
                    logger.info("Kafka send test is fail, exception:$ex")
                }
            }
        )
        assertTrue(result)
    }
}