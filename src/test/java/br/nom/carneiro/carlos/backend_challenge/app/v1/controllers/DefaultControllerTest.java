package br.nom.carneiro.carlos.backend_challenge.app.v1.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DefaultControllerTest {
    @LocalServerPort
    private int PORT;

    private String baseURL() {
        return "http://localhost:" + PORT;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnCorrectText() {
        String result = testRestTemplate.getForObject(baseURL() + "/", String.class, "");
        assertEquals("Back-end Challenge 2021 🏅 - Space Flight News", result);
    }
}
