package com.github.aleksandermielczarek.observablecache2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CacheableObservable<T> implements ObservableTransformer<T, T> {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableObservable(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return observableCache.cacheObservable(key, upstream);
    }
}