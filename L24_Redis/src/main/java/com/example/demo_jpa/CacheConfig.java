package com.example.demo_jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig {

    @Bean
    RedisConnectionFactory getConnection(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
                "redis-18075.crce276.ap-south-1-3.ec2.cloud.redislabs.com", 18075
        );
        lettuceConnectionFactory.setPassword("rb96elsmgZ1JkO1xRi22RhUVH4qdHXzb");
        return lettuceConnectionFactory;
    }

    @Bean
    RedisTemplate<String, Object> getRedisTemplate(){

        // Key - string, value - string, list(string), hash(string, string), set(string)...
        // allIssuedBooks - [(Book 1), (Book 2)]
        // allAuthors - {(Author1, Author2, Author 3)}
        // student:1 - {name: Shafi, age: 20, email: "", ...}

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(getConnection()); // every redis template should have a connection factory attached mandatorily otherwise they won't be executing any commands

        redisTemplate.setKeySerializer(new StringRedisSerializer()); // any key which is set from this code, we will sending a string from the code that we write, the client library will convert it into byte[] and then send it to redis server
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }

    @Bean
    ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
