package study.kstock.stockcore.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.HashMap


@EnableKafka
@Configuration
class KafkaSubscriberConfig {

    @Value(value = "\${bootstrap.servers}")
    private lateinit var bootstrap: String

    fun receiveConsumerFactory(): ConsumerFactory<String, Any> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrap  // Multi Cluster 환경일 경우 여러개 나열 가능
        props[ConsumerConfig.GROUP_ID_CONFIG] = "stock-core"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(),
            JsonDeserializer(Any::class.java, false)
        )
    }

    @Bean
    fun receiveKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, Any> =
            ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.consumerFactory = receiveConsumerFactory()
        return factory
    }
}