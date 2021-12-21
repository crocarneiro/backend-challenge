package br.nom.carneiro.carlos.backend_challenge.infra.mongo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;

@ActiveProfiles("test")
@SpringBootTest
public class ArticleRepositoryImplTests {
    @Autowired
    private ArticleRepositoryImpl repository;

    /* Dummy article 1 */
    private final String title = "NASA promotes East Coast Starship option at LC-49 following SpaceX interest";
    private final String url = "https://www.nasaspaceflight.com/2021/12/starship-lc-49-ksc/";
    private final String imageUrl = "https://www.nasaspaceflight.com/wp-content/uploads/2021/12/Pad_39A_B_C_EE33-1.jpg";
    private final String newsSite = "NASA Spaceflight";
    private final String summary = "The prospect of Starship making its mark on the Space Coast entered another level this week when NASA revealed it would conduct environmental assessments on LC-49 to support Starship launch and landing operations.";
    private final LocalDateTime publishedAt = LocalDateTime.parse("2021-12-17T15:37:56");
    private final Boolean featured = false;

    /* Dummy article 2 */
    private final String title2 = "Malaysia outlines national space blueprint with focus on remote-sensing satellite development";
    private final String url2 = "https://spacenews.com/malaysia-outlines-national-space-blueprint-with-focus-on-remote-sensing-satellite-development/";
    private final String imageUrl2 = "https://spacenews.com/wp-content/uploads/2021/12/Datuk-Ahmad-Amzad-Hashim-copy.jpg";
    private final String newsSite2 = "SpaceNews";
    private final String summary2 = "During a Dec. 13 parliamentary hearing, a deputy minister shared the latest update on the “Malaysia Space Exploration 2030” blueprint being fleshed out by Malaysia’s Ministry of Science, Technology and Innovation.";
    private final LocalDateTime publishedAt2 = LocalDateTime.parse("2021-12-17T14:56:43");
    private final Boolean featured2 = false;

    private Article instantiateDummyArticle1() {
        var article = new Article();
        article.setTitle(title);
        article.setUrl(url);
        article.setImageUrl(imageUrl);
        article.setNewsSite(newsSite);
        article.setSummary(summary);
        article.setPublishedAt(publishedAt);
        article.setFeatured(featured);

        return article;
    }

    @Test
    public void shouldNotFindArticle() {
        assertNull(repository.findById("dajfksdljfs"));
    }

    @Test
    void shouldCreateNewArticle() {
        var article = instantiateDummyArticle1();
        article = repository.save(article);

        assertNotNull(article.createdAt());
        assertNotNull(article.id());
        assertNull(article.modifiedAt());

        assertEquals(title, article.getTitle());
        assertEquals(url, article.getUrl());
        assertEquals(imageUrl, article.getImageUrl());
        assertEquals(newsSite, article.getNewsSite());
        assertEquals(summary, article.getSummary());
        assertEquals(publishedAt.toString(), article.getPublishedAt().toString());
        assertEquals(featured, article.getFeatured());

        assertNull(article.getEvents());
        assertNull(article.getLaunches());
    }

    @Test
    void shouldUpdateArticle() {
        var article = instantiateDummyArticle1();
        article = repository.save(article);

        article.setTitle(title2);
        article.setUrl(url2);
        article.setImageUrl(imageUrl2);
        article.setNewsSite(newsSite2);
        article.setSummary(summary2);
        article.setPublishedAt(publishedAt2);
        article.setFeatured(featured2);

        article = repository.save(article);

        assertNotNull(article.createdAt());
        assertNotNull(article.id());
        assertNotNull(article.modifiedAt());

        assertEquals(title2, article.getTitle());
        assertEquals(url2, article.getUrl());
        assertEquals(imageUrl2, article.getImageUrl());
        assertEquals(newsSite2, article.getNewsSite());
        assertEquals(summary2, article.getSummary());
        assertEquals(publishedAt2.toString(), article.getPublishedAt().toString());
        assertEquals(featured2, article.getFeatured());

        assertNull(article.getEvents());
        assertNull(article.getLaunches());
    }

    @Test
    void shouldDeleteArticle() {
        var article = repository.save(instantiateDummyArticle1());

        repository.delete(article);

        assertNull(repository.findById(article.id()));
    }

    @Test
    void shouldCountBySourceName() {
        var article1 = new Article();
        article1.setSourceName("Space Flight");
        repository.save(article1);

        var article2 = new Article();
        article2.setSourceName("Space Flight");
        repository.save(article2);

        var article3 = new Article();
        article3.setSourceName("Space Flight");
        repository.save(article3);

        var article4 = new Article();
        article4.setSourceName("Random Source");
        repository.save(article4);

        var expected = 3L;
        var actual = repository.countBySourceName("Space Flight");

        assertEquals(expected, actual);
    }
}
