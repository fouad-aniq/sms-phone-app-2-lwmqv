package ai.shreds.infrastructure.config; 
  
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.context.annotation.Bean; 
 import org.springframework.context.annotation.Configuration; 
 import org.springframework.data.redis.connection.RedisConnectionFactory; 
 import org.springframework.data.redis.connection.RedisStandaloneConfiguration; 
 import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory; 
 import org.springframework.data.redis.core.RedisTemplate; 
 import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer; 
 import org.springframework.data.redis.serializer.StringRedisSerializer; 
  
 @Configuration 
 public class InfrastructureRedisConfig { 
  
     @Value("${spring.redis.host}") 
     private String redisHost; 
  
     @Value("${spring.redis.port}") 
     private int redisPort; 
  
     public void configureRedis() { 
         // Configuration is done via beans defined in this class 
         // This method exists to match the UML design 
     } 
  
     @Bean 
     public RedisConnectionFactory redisConnectionFactory() { 
         // Configure the RedisConnectionFactory using Lettuce 
         RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(); 
         redisConfig.setHostName(redisHost); 
         redisConfig.setPort(redisPort); 
         return new LettuceConnectionFactory(redisConfig); 
     } 
  
     @Bean 
     public RedisTemplate<String, Object> redisTemplate() { 
         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>(); 
         redisTemplate.setConnectionFactory(redisConnectionFactory()); 
  
         // Use StringRedisSerializer for keys 
         redisTemplate.setKeySerializer(new StringRedisSerializer()); 
         redisTemplate.setHashKeySerializer(new StringRedisSerializer()); 
  
         // Use GenericJackson2JsonRedisSerializer for values 
         redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); 
         redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer()); 
  
         redisTemplate.afterPropertiesSet(); 
         return redisTemplate; 
     } 
 } 
 