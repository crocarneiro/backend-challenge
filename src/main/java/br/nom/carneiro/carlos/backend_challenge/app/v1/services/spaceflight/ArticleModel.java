package br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight;

import java.time.LocalDateTime;
import java.util.List;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;

public class ArticleModel {
    public Long id;
    public String title;
    public String url;
    public String imageUrl;
    public String newsSite;
    public String summary;
    public String publishedAt;
    public String updatedAt;
    public Boolean featured;
    public List<LaunchModel> launches;
    public List<EventModel> events;

    public ArticleModel() {
    }

    public static Article getArticle(ArticleModel model) {
        var article = new Article();
        article.setOriginId(model.id);
        article.setTitle(model.title);
        article.setUrl(model.url);
        article.setImageUrl(model.imageUrl);
        article.setNewsSite(model.newsSite);
        article.setPublishedAt(LocalDateTime.parse(model.publishedAt.substring(0, 20)));
        article.setFeatured(model.featured);
        article.setEvents(EventModel.eventsFromModel(model));
        article.setLaunches(LaunchModel.eventsFromModel(model));

        return article;
    }
}
