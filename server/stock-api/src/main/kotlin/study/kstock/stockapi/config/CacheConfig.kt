package study.kstock.stockapi.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration
import javax.annotation.Resource

@Configuration
@EnableCaching
class CacheConfig {

    @Resource
    private lateinit var redisConnectionFactory: RedisConnectionFactory

    @Bean
    fun redisCacheManager(): CacheManager? {
        val builder = RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
        ).entryTtl(
            Duration.ofSeconds(30)
        )
        builder.cacheDefaults(redisCacheConfiguration)
        return builder.build()
    }
}