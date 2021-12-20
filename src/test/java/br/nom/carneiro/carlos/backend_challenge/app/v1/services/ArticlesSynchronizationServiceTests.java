package br.nom.carneiro.carlos.backend_challenge.app.v1.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight.SpaceFlightService;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;

@ActiveProfiles("test")
@SpringBootTest
public class ArticlesSynchronizationServiceTests {
    @Autowired
    private ArticlesSynchronizationService underTest;

    @Autowired
    private SpaceFlightService articlesSource;

    @Autowired
    private ArticleRepository repository;

    @Test
    @Disabled
    void shouldJustRun() {
        underTest.sync(articlesSource);

        for(var article : repository.findAll()) {
            System.out.println(article.getTitle());
        }
    }
}
