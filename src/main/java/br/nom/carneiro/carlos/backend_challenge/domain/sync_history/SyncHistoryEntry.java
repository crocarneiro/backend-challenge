package br.nom.carneiro.carlos.backend_challenge.domain.sync_history;

import br.nom.carneiro.carlos.backend_challenge.domain.Entity;

public class SyncHistoryEntry extends Entity {
    private Boolean firstSync;
    private Long articlesSynchronized;
    private Long latestSynchronizedId;

    @Override
    public String toString() {
        return "firstSync: " + firstSync + "\tarticlesSynchronized: " + articlesSynchronized + "\tlatestSynchronizedId: " + latestSynchronizedId;
    }

    public void setLatestSynchronizedId(Long latestSynchronizedId) {
        this.latestSynchronizedId = latestSynchronizedId;
    }

    public void setArticlesSynchronized(Long articlesSynchronized) {
        this.articlesSynchronized = articlesSynchronized;
    }

    public Boolean isTheFirstSync() {
        return firstSync;
    }

    public void setFirstSync(Boolean firstSync) {
        this.firstSync = firstSync;
    }

    public Long getArticlesSynchronized() {
        return articlesSynchronized;
    }

    public Long getLatestSynchronizedId() {
        return latestSynchronizedId;
    }
}
