package com.github.aleksandermielczarek.observablecache;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 01.11.2016.
 */

public final class CacheableObservable<T> implements Observable.Transformer<T, T> {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableObservable(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public Observable<T> call(Observable<T> observable) {
        return observableCache.cacheObservable(key, observable);
    }
}
