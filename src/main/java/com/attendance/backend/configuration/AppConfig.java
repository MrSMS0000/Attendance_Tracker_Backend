package com.attendance.backend.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig{
    public final static String databaseName = "Attendance";
    @Bean
    public MongoClient mongoClient(){
        String connectionString = "mongodb+srv://user:USER@cluster0.zhlg7th.mongodb.net/?retryWrites=true&w=majority";
        return MongoClients.create(connectionString);
    }
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient){
        return new MongoTemplate(mongoClient,AppConfig.databaseName);
    }

}
