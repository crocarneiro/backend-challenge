package br.nom.carneiro.carlos.backend_challenge.app.v1.models;

import java.util.ArrayList;
import java.util.List;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
import br.nom.carneiro.carlos.backend_challenge.domain.article.Launch;

public class LaunchModel {
    public String id;
    public String provider;

    public static List<LaunchModel> launchesFromArticle(Article article) {
        var list = new ArrayList<LaunchModel>();

        if(article.getLaunches() == null) return list;

        for(var launch : article.getLaunches()) {
            var launchModel = new LaunchModel();
            launchModel.id = launch.getId();
            launchModel.provider = launch.getProvider();

            list.add(launchModel);
        }

        return list;
    }

    public static List<Launch> eventsFromModel(ArticleModel model) {
        var list = new ArrayList<Launch>();

        for(var launch : model.launches) {
            var newLaunch = new Launch();
            newLaunch.setId(launch.id);
            newLaunch.setProvider(launch.provider);

            list.add(newLaunch);
        }

        return list;
    }
}
