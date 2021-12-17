package br.nom.carneiro.carlos.backend_challenge.domain.articles_sincronization;

import java.time.LocalDateTime;

import br.nom.carneiro.carlos.backend_challenge.domain.Entity;

public class Sync extends Entity {
    private LocalDateTime lastSynchronizationDateTime;

    public LocalDateTime lastSynchronizationDateTime() {
        return lastSynchronizationDateTime;
    }

    public void lastSynchronizationDateTime(LocalDateTime lastSynchronizationDateTime) {
        this.lastSynchronizationDateTime = lastSynchronizationDateTime;
    }
}
