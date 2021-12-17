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
    public List<Article> getBunchOfArticles(Integer start, Integer amount) {
        var result = new ArrayList<Article>();

        var restTemplate = new RestTemplate();
        var response = restTemplate.exchange(BASE_URL + "?_start=" + start + "&_limit=" + amount, HttpMethod.GET, null, String.class);
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
