package br.nom.carneiro.carlos.backend_challenge.domain.sync_history;

import br.nom.carneiro.carlos.backend_challenge.domain.Entity;

public class SyncHistoryEntry extends Entity {
    private Boolean firstSync;

    private Long articlesSynchronized;

    public Long getArticlesSynchronized() {
        return articlesSynchronized;
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
}
