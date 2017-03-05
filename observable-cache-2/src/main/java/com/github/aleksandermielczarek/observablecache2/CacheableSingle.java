package com.github.aleksandermielczarek.observablecache2;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CacheableSingle<T> implements SingleTransformer<T, T> {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableSingle(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return observableCache.cacheSingle(key, upstream);
    }
}