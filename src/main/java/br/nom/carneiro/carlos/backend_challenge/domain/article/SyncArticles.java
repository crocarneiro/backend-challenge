package br.nom.carneiro.carlos.backend_challenge.domain.article;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class takes an implementation of ArticleSource and
 */
public class SyncArticles {
    private LocalDateTime lastSynchronizationDateTime;

    public LocalDateTime lastSynchronizationDateTime() {
        return lastSynchronizationDateTime;
    }

    public void lastSynchronizationDateTime(LocalDateTime lastSynchronizationDateTime) {
        this.lastSynchronizationDateTime = lastSynchronizationDateTime;
    }

    public static void syncAll() {
        Integer nThreads = 5; /* Number of threads synching the articles. */
        Integer step = 30; /* Each thread will sync 10 articles per time. */

        Integer skip = 0; /* Variable which holds the number of records being skipped. */
        var threads = new ArrayList<Thread>();

        for(int i = 0; i < nThreads; i++) {
            var t = new Thread(() -> {

            });

            threads.add(t);
        }
    }
}
