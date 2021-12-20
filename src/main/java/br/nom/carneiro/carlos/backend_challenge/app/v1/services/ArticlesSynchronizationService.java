package br.nom.carneiro.carlos.backend_challenge.app.v1.services;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticleRepository;
import br.nom.carneiro.carlos.backend_challenge.domain.article.ArticlesSource;

@Service
public class ArticlesSynchronizationService {
    @Autowired
    private ArticleRepository repository;

    /**
     * This method takes an implementation of ArticlesSource and synchronize all of its articles with our own database.
     *
     * @param source An implementation of ArticleSource.
     * @see ArticlesSource
     */
    public void sync(ArticlesSource source) {
        var nThreads = 5; /* Number of threads synching the articles. */
        var step = 50L; /* Number of articles to be sync by the threads every run. */
        var skip = new AtomicLong(0); /* Variable which holds the number of records being skipped. */
        var articlesToSync = source.getNumberOfArticles(); /* Get the total number of articles to sync. */

        /* Create threads (just create, not running yet). */
        var threads = new ArrayList<Thread>();
        for(int i = 0; i < nThreads; i++) {
            var t = new Thread(() -> {
                if(skip.get() >= articlesToSync) return; /* If there is nothing more to sync, then that's it. */

                var articles = source.getBunchOfArticles(skip.get(), step);
                var next = skip.get() + articles.size();
                skip.set(next);

                for(var article : articles) {
                    repository.save(article);
                }

                System.out.println(next + " articles successfully synchronized.");
            });
            threads.add(t);
        }

        /* Run threads until we got nothing more to sync. */
        while(skip.get() <= articlesToSync) {
            for(var thread : threads)
                thread.run();
        }
    }
}
