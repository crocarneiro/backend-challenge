package br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight;

import java.util.ArrayList;
import java.util.List;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
import br.nom.carneiro.carlos.backend_challenge.domain.article.Event;

public class EventModel {
    public String id;
    public String provider;

    public static List<EventModel> eventsFromArticle(Article article) {
        var list = new ArrayList<EventModel>();
        for(var event : article.getEvents()) {
            var eventModel = new EventModel();
            eventModel.id = event.getId();
            eventModel.provider = event.getProvider();

            list.add(eventModel);
        }

        return list;
    }

    public static List<Event> eventsFromModel(ArticleModel model) {
        var list = new ArrayList<Event>();

        for(var event : model.events) {
            var newEvent = new Event();
            newEvent.setId(event.id);
            newEvent.setProvider(event.provider);

            list.add(newEvent);
        }

        return list;
    }
}
