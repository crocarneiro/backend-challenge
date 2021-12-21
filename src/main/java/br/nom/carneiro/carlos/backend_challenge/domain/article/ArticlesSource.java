package br.nom.carneiro.carlos.backend_challenge.domain.article;

import java.util.List;

public interface ArticlesSource {
    public List<Article> getArticles(Long start, Long amount);
    public List<Article> getArticlesPublishedAfter(Long start, Long amount, Long id);
    public Long getNumberOfArticles();
    public String getSourceName();
}
