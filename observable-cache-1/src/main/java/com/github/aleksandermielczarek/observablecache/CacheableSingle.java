package com.github.aleksandermielczarek.observablecache;

import rx.Single;

/**
 * Created by Aleksander Mielczarek on 01.11.2016.
 */

public final class CacheableSingle<T> implements Single.Transformer<T, T> {

    private final String key;
    private final ObservableCache observableCache;

    public CacheableSingle(String key, ObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public Single<T> call(Single<T> single) {
        return observableCache.cacheSingle(key, single);
    }
}
