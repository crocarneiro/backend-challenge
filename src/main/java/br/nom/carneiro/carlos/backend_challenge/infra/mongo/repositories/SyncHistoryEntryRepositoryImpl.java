package br.nom.carneiro.carlos.backend_challenge.infra.mongo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.nom.carneiro.carlos.backend_challenge.domain.sync_history.SyncHistoryEntry;
import br.nom.carneiro.carlos.backend_challenge.domain.sync_history.SyncHistoryEntryRepository;

@Repository
public class SyncHistoryEntryRepositoryImpl implements SyncHistoryEntryRepository {
    private static final String COLLECTION_NAME = "syncHistory";

    @Autowired
    private MongoTemplate template;

    @Override
    public SyncHistoryEntry save(SyncHistoryEntry syncHistoryEntry) {
        return template.save(syncHistoryEntry, COLLECTION_NAME);
    }

    @Override
    public List<SyncHistoryEntry> findAll() {
        return template.findAll(SyncHistoryEntry.class, COLLECTION_NAME);
    }

    @Override
    public SyncHistoryEntry findLatest() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "$natural"));

        return template.findOne(query, SyncHistoryEntry.class, COLLECTION_NAME);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return template.count(query, SyncHistoryEntry.class, COLLECTION_NAME);
    }
}
