package com.example.demo_jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Repository
public class BookCacheRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    // book::1
    // {id: 1, name: Intro to C++, ...}

    // student::1

    private final String REDIS_KEY_PREFIX = "book::";

    private static Logger logger = LoggerFactory.getLogger(BookCacheRepository.class);

    private String getRedisKey(Integer bookId){
        return REDIS_KEY_PREFIX + bookId;
    }

    public void addBook(Book book) {

        logger.info("Inside addBook function: thread - " + Thread.currentThread().getName());
        this.redisTemplate.opsForValue().set(getRedisKey(book.getId()), book);

//        this.redisTemplate.opsForList().leftPush(getRedisKey(book.getId()), book);
    }

    public void addBookAsHash(Book book) {

        Map<String, Object> map = this.objectMapper.convertValue(book, Map.class);
        this.redisTemplate.opsForHash().putAll(getRedisKey(book.getId()), map);
    }

    public Book getBook(Integer bookId) {
        String key = getRedisKey(bookId);
        return (Book) this.redisTemplate.opsForValue().get(key);
    }

    public Book getBookAsHash(Integer bookId) {
        String key = getRedisKey(bookId);
        Map result = this.redisTemplate.opsForHash().entries(key);
        return this.objectMapper.convertValue(result, Book.class);
    }


}
