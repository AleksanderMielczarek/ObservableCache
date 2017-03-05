package com.github.aleksandermielczarek.observablecache2;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CacheableCompletable implements CompletableTransformer {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableCompletable(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return observableCache.cacheCompletable(key, upstream);
    }
}