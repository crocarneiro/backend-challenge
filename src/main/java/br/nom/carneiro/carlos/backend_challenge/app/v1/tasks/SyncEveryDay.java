package br.nom.carneiro.carlos.backend_challenge.app.v1.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.nom.carneiro.carlos.backend_challenge.app.v1.services.ArticlesSynchronizationService;
import br.nom.carneiro.carlos.backend_challenge.app.v1.services.spaceflight.SpaceFlightService;

@Profile({"dev", "production"})
@Component
public class SyncEveryDay {
    @Autowired
    private ArticlesSynchronizationService syncService;

    @Autowired
    private SpaceFlightService spaceFlightSource;

    @Scheduled(cron = "0 0 9 1/1 * ?")
    public void syncSpaceFlight() {
        syncService.sync(spaceFlightSource);
    }
}
