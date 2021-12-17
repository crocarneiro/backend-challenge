package br.nom.carneiro.carlos.backend_challenge.infra.mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Profile("dev")
public class MongoConfigDev extends AbstractMongoClientConfiguration {
    @Value("${mongo.database.url:}")
    private String databaseUrl;

    @Container
    private MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.4"));

    @Override
    public @Bean MongoClient mongoClient() {
        if(databaseUrl != null && databaseUrl.length() > 0) {
            return MongoClients.create(databaseUrl);
        } else {
            mongoDBContainer.start();
            return MongoClients.create(mongoDBContainer.getReplicaSetUrl());
        }
    }

    @Override
    public String getDatabaseName() {
        if(databaseUrl != null && databaseUrl.length() > 0)
            return "dev";
        return "test";
    }
}
