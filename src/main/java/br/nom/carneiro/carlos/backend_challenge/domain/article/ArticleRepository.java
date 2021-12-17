package br.nom.carneiro.carlos.backend_challenge.domain.article;

import java.util.List;

public interface ArticleRepository {
    public Article save(Article article);
    public Article findById(String id);
    public List<Article> findAll();
    public void delete(Article article);
}
