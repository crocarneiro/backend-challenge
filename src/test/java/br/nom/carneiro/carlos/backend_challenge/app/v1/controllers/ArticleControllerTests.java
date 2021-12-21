package br.nom.carneiro.carlos.backend_challenge.app.v1.controllers;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.nom.carneiro.carlos.backend_challenge.app.v1.models.ArticleModel;
import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    @LocalServerPort
    private int PORT;

    private String baseURL() {
        return "http://localhost:" + PORT;
    }

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ArticleRepository repository;

    @Test
    public void shouldSaveNewArticle() {
        String json =
            """
            {
            \"title\": \"No commercial crew test flights expected this year\",
            \"url\": \"No commercial crew test flights expected this year\",
            \"imageUrl\": \"https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2018/10/ccp-countdown-header-326x245.jpg\",
            \"newsSite\": \"Caceta e planeta\",
            \"summary\": null,
            \"publishedAt\": \"2018-10-05T22:00:00\",
            \"featured\": false,
            \"launches\": [],
            \"events\": []
            }
            """;
        var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        var request = new HttpEntity<String>(json, headers);
        ResponseEntity<ArticleModel> response = template.exchange(
            baseURL() + "/articles/",
            HttpMethod.POST,
            request,
            ArticleModel.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().id);
    }

    @Test
    public void shouldGetAll() throws JSONException {
        repository.save(new Article());
        repository.save(new Article());
        repository.save(new Article());

        var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        var request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = template.exchange(
            baseURL() + "/articles/",
            HttpMethod.GET,
            request,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONObject json = new JSONObject(response.getBody());
        assertEquals(3, json.getInt("numberOfElements"));
    }

    @Test
    public void shouldDelete() {
        var article = repository.save(new Article());

        var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        var request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = template.exchange(
            baseURL() + "/articles/" + article.id(),
            HttpMethod.DELETE,
            request,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(repository.findById(article.id()));
    }

    @Test
    public void shouldUpdate() {
        var article = repository.save(new Article());

        String json =
            """
            {
            \"title\": \"No commercial crew test flights expected this year\",
            \"url\": \"No commercial crew test flights expected this year\",
            \"imageUrl\": \"https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2018/10/ccp-countdown-header-326x245.jpg\",
            \"newsSite\": \"Caceta e planeta\",
            \"summary\": null,
            \"publishedAt\": \"2018-10-05T22:00:00\",
            \"featured\": false,
            \"launches\": [],
            \"events\": []
            }
            """;

        var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        var request = new HttpEntity<String>(json, headers);
        ResponseEntity<ArticleModel> response = template.exchange(
            baseURL() + "/articles/" + article.id(),
            HttpMethod.PUT,
            request,
            ArticleModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertEquals(article.id(), body.id);
        assertEquals("No commercial crew test flights expected this year", body.title);
        assertEquals("https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2018/10/ccp-countdown-header-326x245.jpg", body.imageUrl);
    }
}
