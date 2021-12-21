package br.nom.carneiro.carlos.backend_challenge.app.v1.services;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticlesSource;
import br.nom.carneiro.carlos.backend_challenge.domain.sync_history.SyncHistoryEntry;
import br.nom.carneiro.carlos.backend_challenge.domain.sync_history.SyncHistoryEntryRepository;

@Service
public class ArticlesSynchronizationService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SyncHistoryEntryRepository historyRepository;

    /**
     * This method takes an implementation of ArticlesSource and synchronize all of its articles with our own database.
     *
     * @param source An implementation of ArticleSource.
     * @see ArticlesSource
     */
    public void sync(ArticlesSource source) {
        System.out.println("Starting articles synchronization...");
        var nThreads = 10; /* Number of threads synching the articles. This number is kind of random. */
        var skip = new AtomicLong(0); /* Variable which holds the number of records being skipped. */
        var latestSynchronizedId = new AtomicLong(0); /* Keeps the ID (from spaceflight) of the latest synchronized article. */

        System.out.println("Getting the total number of articles in the source...");
        var articlesOnOrigin = source.getNumberOfArticles(); /* Get the total number of articles to sync. */
        System.out.println("Total number of articles in the source: " + articlesOnOrigin);

        System.out.println("Getting the total number of articles from this source in our database...");
        var articlesFromOrigin = articleRepository.countBySourceName(source.getSourceName());
        System.out.println("Total number of articles from the source in our database: " + articlesFromOrigin);

        System.out.println("Calculating the number of articles with syncle pending...");
        var articlesPending = articlesOnOrigin - articlesFromOrigin;
        System.out.println("Number of articles with sync pending: " + articlesPending);
        skip.set(articlesFromOrigin);

        var step = (long)Math.ceil(articlesOnOrigin / nThreads) + 1; /* Number of articles to be sync by the threads every run. */
        System.out.println("Each thread will sync " + step + " articles.");

        System.out.println("Getting information about our last articles synchronization...");
        var latestEntry = historyRepository.findLatest();
        if(latestEntry != null) {
            System.out.println(latestEntry.toString());
        } else {
            System.out.println("This is the first synchronization ever.");
        }

        /* Create threads (just create, not running yet). */
        var threads = new ArrayList<Thread>();
        for(int i = 0; i < nThreads; i++) {
            var t = new Thread(() -> {
                System.out.println("Starting new thread...");
                var currentSkip = skip.getAndAdd(step);

                System.out.println("Synchronization will start from article " + currentSkip);
                var articles = source.getArticles(currentSkip, step);

                articles.parallelStream().forEach(article -> articleRepository.save(article));

                if(articles.size() > 0) {
                    latestSynchronizedId.set(articles.get(articles.size() - 1).getOriginId());
                    System.out.println(currentSkip + articles.size() + " articles successfully synchronized.");
                }
                else {
                    System.out.println("Nothing new to synchronize.");
                    return;
                }
            });
            threads.add(t);
        }

        /* Run the threads in parallelism. */
        threads.parallelStream().forEach(t -> t.run());

        /* Saves the synchronization history. */
        var newHistoryEntry = new SyncHistoryEntry();
        newHistoryEntry.setFirstSync((latestEntry != null) ? false : true);
        newHistoryEntry.setArticlesSynchronized(skip.get());
        newHistoryEntry.setLatestSynchronizedId(latestSynchronizedId.get());
        newHistoryEntry = historyRepository.save(newHistoryEntry);

        System.out.println("Successfully finished synchronization.");
        System.out.println("ID of the log entry: " + newHistoryEntry.id());
        System.out.println("Last synchronized origin ID: " + latestSynchronizedId.get());
    }
}
