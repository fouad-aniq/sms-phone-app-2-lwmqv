package ai.shreds.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;

import ai.shreds.domain.entities.DomainScheduleEntity;

@Configuration
public class InfrastructureRedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    public void configureRedis() {
        // Additional configurations or initializations can be performed here if needed
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        try {
            // Standalone Redis configuration
            RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);

            // Socket options for performance tuning
            SocketOptions socketOptions = SocketOptions.builder()
                    .keepAlive(true)
                    .build();

            // Timeout options for commands
            TimeoutOptions timeoutOptions = TimeoutOptions.builder()
                    .fixedTimeout(java.time.Duration.ofSeconds(10))
                    .build();

            ClientOptions clientOptions = ClientOptions.builder()
                    .socketOptions(socketOptions)
                    .timeoutOptions(timeoutOptions)
                    .build();

            // Client configuration with pooling
            LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                    .clientResources(clientResources)
                    .clientOptions(clientOptions)
                    .build();

            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
            return lettuceConnectionFactory;
        } catch (Exception e) {
            // Handle exceptions related to cache access
            throw new RuntimeException("Failed to create RedisConnectionFactory", e);
        }
    }

    @Bean
    public RedisTemplate<String, DomainScheduleEntity> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        try {
            RedisTemplate<String, DomainScheduleEntity> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);

            // Use String serializer for keys
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());

            // Use JSON serializer for values
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        } catch (Exception e) {
            // Handle exceptions related to cache access
            throw new RuntimeException("Failed to create RedisTemplate", e);
        }
    }
}
