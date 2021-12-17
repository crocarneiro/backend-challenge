package br.nom.carneiro.carlos.backend_challenge.domain.article;

import java.util.List;

public interface ArticlesSource {
    public List<Article> getBunchOfArticles(Integer start, Integer amount);
}
