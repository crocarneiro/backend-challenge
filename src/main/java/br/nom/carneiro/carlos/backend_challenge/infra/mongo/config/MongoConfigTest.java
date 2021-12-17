package br.nom.carneiro.carlos.backend_challenge.infra.mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Profile("test")
public class MongoConfigTest extends AbstractMongoClientConfiguration {
    @Container
    private MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.4"));

    @Override
    public @Bean MongoClient mongoClient() {
        mongoDBContainer.start();
        return MongoClients.create(mongoDBContainer.getReplicaSetUrl());
    }

    @Override
    public String getDatabaseName() {
        return "test";
    }
}
