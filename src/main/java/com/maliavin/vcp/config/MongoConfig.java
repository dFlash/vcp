package com.maliavin.vcp.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * Configuration for connection to mongo db
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Configuration
@EnableMongoRepositories("com.maliavin.vcp.repository.storage")
public class MongoConfig {

    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.port}")
    private int mongoPort;

    public @Bean MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient("localhost", mongoPort);
    }

    public @Bean MongoTemplate mongoTemplate(@Value("${mongo.db}") String mongoDb) throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), mongoDb);
        return mongoTemplate;
    }

}