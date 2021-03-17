package study.kstock.stockexternal.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.*


@Configuration
class KafkaPublisherConfig {
    @Value(value = "\${bootstrap.servers}")
    private lateinit var bootstrap: String   // 현재는 단일 서버
    @Bean
    fun sendProducerFactory(): ProducerFactory<String, Any> {
        val configProps = HashMap<String, Any?>()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrap
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun sendKafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(sendProducerFactory())
    }
}