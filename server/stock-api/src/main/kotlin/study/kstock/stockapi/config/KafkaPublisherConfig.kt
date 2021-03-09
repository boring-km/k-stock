package study.kstock.stockapi.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import study.kstock.stockapi.domain.Ping
import java.util.HashMap


@Configuration
class KafkaPublisherConfig {
    @Value(value = "\${kafka.bootstrap}")
    private val bootstrap: String? = null
    @Bean
    fun pingProducerFactory(): ProducerFactory<String, Ping> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrap
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun pingKafkaTemplate(): KafkaTemplate<String, Ping> {
        return KafkaTemplate(pingProducerFactory())
    }
}