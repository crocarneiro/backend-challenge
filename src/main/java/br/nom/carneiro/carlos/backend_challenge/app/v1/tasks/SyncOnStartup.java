package br.nom.carneiro.carlos.backend_challenge.app.v1.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.nom.carneiro.carlos.backend_challenge.app.v1.services.ArticlesSynchronizationService;
import br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight.SpaceFlightService;

@Profile({"dev", "production"})
@Component
public class SyncOnStartup implements ApplicationListener<ApplicationReadyEvent>{
    @Autowired
    private ArticlesSynchronizationService syncService;

    @Autowired
    private SpaceFlightService source;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        syncService.sync(source);
    }
}
