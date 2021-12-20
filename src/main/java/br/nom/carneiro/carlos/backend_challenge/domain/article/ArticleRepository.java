package br.nom.carneiro.carlos.backend_challenge.domain.article;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ArticleRepository {
    public Article save(Article article);
    public Article findById(String id);
    public List<Article> findAll();
    public Page<Article> findAll(int page);
    public void delete(Article article);
}
