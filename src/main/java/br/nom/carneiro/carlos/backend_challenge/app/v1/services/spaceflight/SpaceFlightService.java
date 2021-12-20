package br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticlesSource;

@Service
public class SpaceFlightService implements ArticlesSource {
    private static final String BASE_URL = "https://api.spaceflightnewsapi.net/v3/articles";

    @Override
    public List<Article> getArticles(Long start, Long amount) {
        return findArticles(start, amount, null);
    }

    @Override
    public List<Article> getArticlesPublishedAfter(Long start, Long amount, Long id) {
        return findArticles(start, amount, id);
    }

    @Override
    public Long getNumberOfArticles() {
        var result = 0L;

        var restTemplate = new RestTemplate();
        var response = restTemplate.exchange(BASE_URL + "/count", HttpMethod.GET, null, String.class);

        result = Long.parseLong(response.getBody());

        return result;
    }

    private List<Article> findArticles(Long start, Long amount, Long id) {
        var result = new ArrayList<Article>();

        var parameters = "?_start=" + start + "&_limit=" + amount + "&_sort=id";
        if(id != null) parameters += "&id_gt=" + id;

        var restTemplate = new RestTemplate();
        var response = restTemplate.exchange(BASE_URL + parameters, HttpMethod.GET, null, String.class);
        var objectMapper = new ObjectMapper();

        try {
            List<ArticleModel> articlesModels = objectMapper.readValue(response.getBody(), new TypeReference<List<ArticleModel>>() {});

            for(var articleModel : articlesModels) {
                result.add(ArticleModel.getArticle(articleModel));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
