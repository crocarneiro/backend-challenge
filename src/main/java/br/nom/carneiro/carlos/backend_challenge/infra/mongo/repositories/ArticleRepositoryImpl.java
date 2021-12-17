package br.nom.carneiro.carlos.backend_challenge.infra.mongo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private static String COLLECTION_NAME = "articles";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Article save(Article article) {
        if(article.id() != null) article.modifiedNow();

        return mongoTemplate.save(article, COLLECTION_NAME);
    }

    @Override
    public Article findById(String id) {
        return mongoTemplate.findById(id, Article.class, COLLECTION_NAME);
    }

    @Override
    public List<Article> findAll() {
        return mongoTemplate.findAll(Article.class, COLLECTION_NAME);
    }

    @Override
    public void delete(Article article) {
        mongoTemplate.remove(article, COLLECTION_NAME);
    }
}
