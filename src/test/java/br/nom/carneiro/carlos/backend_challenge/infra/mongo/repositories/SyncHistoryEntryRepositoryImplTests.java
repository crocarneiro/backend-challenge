package br.nom.carneiro.carlos.backend_challenge.infra.mongo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import br.nom.carneiro.carlos.backend_challenge.domain.sync_history.SyncHistoryEntry;

@ActiveProfiles("test")
@SpringBootTest
public class SyncHistoryEntryRepositoryImplTests {
    @Autowired
    private SyncHistoryEntryRepositoryImpl underTest;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void cleanCollection() {
        mongoTemplate.dropCollection("syncHistory");
    }

    @Test
    public void shouldSaveEntry() {
        var articlesSynchronized = 50L;
        var firstSync = true;

        var newEntry = new SyncHistoryEntry();
        newEntry.setArticlesSynchronized(articlesSynchronized);
        newEntry.setFirstSync(firstSync);

        newEntry = underTest.save(newEntry);

        assertNotNull(newEntry.id());
        assertNotNull(newEntry.createdAt());

        assertEquals(articlesSynchronized, newEntry.getArticlesSynchronized());
        assertEquals(firstSync, newEntry.isTheFirstSync());

        assertNull(newEntry.modifiedAt());
    }

    @Test
    public void shouldGetTheLastRecord() {
        var articlesSynchronized = 55L;
        var firstSync = false;

        var newEntry = new SyncHistoryEntry();
        newEntry.setArticlesSynchronized(50L);
        newEntry.setFirstSync(true);
        newEntry = underTest.save(newEntry);

        var newEntry2 = new SyncHistoryEntry();
        newEntry2.setArticlesSynchronized(49L);
        newEntry2.setFirstSync(false);
        newEntry2 = underTest.save(newEntry2);

        var newEntry3 = new SyncHistoryEntry();
        newEntry3.setArticlesSynchronized(articlesSynchronized);
        newEntry3.setFirstSync(firstSync);
        newEntry3 = underTest.save(newEntry3);

        var latestEntry = underTest.findLatest();

        assertEquals(articlesSynchronized, latestEntry.getArticlesSynchronized());
        assertEquals(firstSync, latestEntry.isTheFirstSync());
    }

    @Test
    public void shouldCountCorrectly() {
        var newEntry = new SyncHistoryEntry();
        newEntry.setArticlesSynchronized(50L);
        newEntry.setFirstSync(true);
        newEntry = underTest.save(newEntry);

        var newEntry2 = new SyncHistoryEntry();
        newEntry2.setArticlesSynchronized(49L);
        newEntry2.setFirstSync(false);
        newEntry2 = underTest.save(newEntry2);

        var newEntry3 = new SyncHistoryEntry();
        newEntry3.setArticlesSynchronized(15L);
        newEntry3.setFirstSync(false);
        newEntry3 = underTest.save(newEntry3);

        assertEquals(3L, underTest.count());
    }
}
