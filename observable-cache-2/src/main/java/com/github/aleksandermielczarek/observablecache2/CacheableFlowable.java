package com.github.aleksandermielczarek.observablecache2;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CacheableFlowable<T> implements FlowableTransformer<T, T> {

    private final String key;
    private final AbstractObservableCache observableCache;

    public CacheableFlowable(String key, AbstractObservableCache observableCache) {
        this.key = key;
        this.observableCache = observableCache;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return observableCache.cacheFlowable(key, upstream);
    }

}