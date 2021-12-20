package br.nom.carneiro.carlos.backend_challenge.domain.sync_history;

import java.util.List;

public interface SyncHistoryEntryRepository {
    public SyncHistoryEntry save(SyncHistoryEntry syncHistoryEntry);
    public SyncHistoryEntry findLatest();
    public List<SyncHistoryEntry> findAll();
    public Long count();
}
