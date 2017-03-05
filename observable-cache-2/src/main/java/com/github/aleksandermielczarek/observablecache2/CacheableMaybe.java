package com.github.aleksandermielczarek.observablecache2;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CacheableMaybe<T> implements MaybeTransformer<T, T> {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableMaybe(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return observableCache.cacheMaybe(key, upstream);
    }
}