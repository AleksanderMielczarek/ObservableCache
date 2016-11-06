package com.github.aleksandermielczarek.observablecache;

import rx.Completable;

/**
 * Created by Aleksander Mielczarek on 01.11.2016.
 */

public final class CacheableCompletable implements Completable.Transformer {

    private final String key;
    private final ObservableCache observableCache;

    public CacheableCompletable(String key, ObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public Completable call(Completable completable) {
        return observableCache.cacheCompletable(key, completable);
    }
}
