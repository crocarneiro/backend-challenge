package br.nom.carneiro.carlos.backend_challenge.infra.mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@Profile("production")
public class MongoConfigProduction extends AbstractMongoClientConfiguration {

    @Value("${mongo.database.url}")
    private String databaseUrl;

    @Override
    public @Bean MongoClient mongoClient() {
        return MongoClients.create(databaseUrl);
    }

    @Override
    protected String getDatabaseName() {
        return "prod";
    }
}
