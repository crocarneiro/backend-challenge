package br.nom.carneiro.carlos.backend_challenge.app.v1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.nom.carneiro.carlos.backend_challenge.domain.article.Article;
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

        var step = (long)Math.ceil(articlesOnOrigin / nThreads); /* Number of articles to be sync by the threads every run. */

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
                System.out.println("Starting from " + skip.get());

                var currentSkip = skip.getAndAdd(step);

                if(currentSkip >= articlesOnOrigin) return; /* If there is nothing more to sync, then that's it. */

                /* If it's the first sync, then sync all the articles. If not, sync only the articles who were not synchronized yet. */
                List<Article> articles = null;
                if(latestEntry != null)
                    articles = source.getArticlesPublishedAfter(currentSkip, step, latestEntry.getLatestSynchronizedId());
                else
                    articles = source.getArticles(currentSkip, step);

                for(var article : articles) {
                    articleRepository.save(article);
                }

                if(articles.size() > 0)
                    latestSynchronizedId.set(articles.get(articles.size() - 1).getOriginId());

                System.out.println(currentSkip + articles.size() + " articles successfully synchronized.");
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
