package br.nom.carneiro.carlos.backend_challenge.app.v1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.nom.carneiro.carlos.backend_challenge.app.v1.models.ArticleModel;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;

@RestController
public class ArticleController {
    @Autowired
    private ArticleRepository repository;

    @GetMapping("/articles/")
    public List<ArticleModel> getAll() {
        var articles = repository.findAll();
        var articlesModels = new ArrayList<ArticleModel>();
        for(var article : articles)
            articlesModels.add(new ArticleModel(article));

        return articlesModels;
    }

    @GetMapping("/articles/{id}")
    public ArticleModel getById(@PathVariable("id") String id) {
        var article = repository.findById(id);

        return new ArticleModel(article);
    }

    @PostMapping("/articles/")
    public ArticleModel create(@RequestBody ArticleModel requestBody) {
        var article = ArticleModel.getArticle(requestBody);
        repository.save(article);

        return requestBody;
    }

    @PutMapping("/articles/{id}")
    public ArticleModel update(@RequestBody ArticleModel requestBody, @PathVariable("id") String id) {
        var article = repository.findById(id);

        var newArticle = ArticleModel.getArticle(requestBody);
        newArticle.id(article.id());
        newArticle.createdAt(article.createdAt());
        newArticle = repository.save(article);

        return new ArticleModel(newArticle);
    }

    @DeleteMapping("/articles/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.delete(repository.findById(id));
    }
}
