package br.nom.carneiro.carlos.backend_challenge.app.v1.models;

import java.time.LocalDateTime;
import java.util.List;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;

public class ArticleModel {
    public String title;
    public String url;
    public String imageUrl;
    public String newsSite;
    public String summary;
    public LocalDateTime publishedAt;
    public Boolean featured;
    public List<LaunchModel> launches;
    public List<EventModel> events;

    public ArticleModel(Article article) {
        title = article.getTitle();
        url = article.getTitle();
        imageUrl = article.getImageUrl();
        newsSite = article.getNewsSite();
        summary = article.getSummary();
        publishedAt = article.getPublishedAt();
        featured = article.getFeatured();
        launches = LaunchModel.launchesFromArticle(article);
        events = EventModel.eventsFromArticle(article);
    }

    public static Article getArticle(ArticleModel model) {
        var article = new Article();
        article.setTitle(model.title);
        article.setUrl(model.url);
        article.setImageUrl(model.imageUrl);
        article.setNewsSite(model.newsSite);
        article.setPublishedAt(model.publishedAt);
        article.setFeatured(model.featured);
        article.setEvents(EventModel.eventsFromModel(model));
        article.setLaunches(LaunchModel.eventsFromModel(model));

        return article;
    }
}
