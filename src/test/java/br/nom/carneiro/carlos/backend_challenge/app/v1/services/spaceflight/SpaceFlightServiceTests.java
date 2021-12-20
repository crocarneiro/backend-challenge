package br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class SpaceFlightServiceTests {
    @Autowired
    SpaceFlightService underTest;

    @Test
    @Disabled
    void shouldJustRun() {
        for(var article:underTest.getArticles(11606L, 10L)) {
            System.out.println(article.getTitle());
        }
    }
}
