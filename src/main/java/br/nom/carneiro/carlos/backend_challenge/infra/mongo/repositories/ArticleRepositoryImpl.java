package br.nom.carneiro.carlos.backend_challenge.infra.mongo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
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
    public Page<Article> findAll(int page) {
        Pageable pageable = PageRequest.of(0, 10);
        var query = new Query().with(pageable);
        var articles = mongoTemplate.find(query, Article.class, COLLECTION_NAME);

        Page<Article> pages = PageableExecutionUtils.getPage(
            articles,
            pageable,
            () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Article.class, COLLECTION_NAME)
        );

        return pages;
    }

    @Override
    public void delete(Article article) {
        mongoTemplate.remove(article, COLLECTION_NAME);
    }

    @Override
    public Long countBySourceName(String sourceName) {
        var query = new Query();
        query.addCriteria(Criteria.where("sourceName").is(sourceName));

        return mongoTemplate.count(Query.of(query), Article.class, COLLECTION_NAME);
    }
}
