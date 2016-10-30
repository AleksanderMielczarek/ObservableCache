package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

abstract class ValueFromCache<T, S extends ValueInCacheAction<T>, U extends ValueNotInCacheAction> {

    @Nullable
    private final T valueFromCache;
    private final ObservableCache observableCache;

    ValueFromCache(@Nullable T valueFromCache, ObservableCache observableCache) {
        this.valueFromCache = valueFromCache;
        this.observableCache = observableCache;
    }

    public boolean isPresent() {
        return valueFromCache != null;
    }

    public ValueFromCache<T, S, U> ifPresent(S valueInCacheAction) {
        if (isPresent()) {
            valueInCacheAction.action(valueFromCache);
        }
        return this;
    }

    public ValueFromCache<T, S, U> orElse(U valueNotInCacheAction) {
        if (!isPresent()) {
            valueNotInCacheAction.action();
        }
        return this;
    }

    public <V> ObservableFromCache<V> orElseGetObservable(String key) {
        if (!isPresent()) {
            return observableCache.getObservable(key);
        }
        return new ObservableFromCache<>(null, observableCache);
    }

    public <V> ObservableFromCache<V> orElseGetObservable(String key, Class<V> observableClass) {
        if (!isPresent()) {
            return observableCache.getObservable(key, observableClass);
        }
        return new ObservableFromCache<>(null, observableCache);
    }

    public <V> SingleFromCache<V> orElseGetSingle(String key) {
        if (!isPresent()) {
            return observableCache.getSingle(key);
        }
        return new SingleFromCache<>(null, observableCache);
    }

    public <V> SingleFromCache<V> orElseGetSingle(String key, Class<V> singleClass) {
        if (!isPresent()) {
            return observableCache.getSingle(key, singleClass);
        }
        return new SingleFromCache<>(null, observableCache);
    }

    public CompletableFromCache orElseGetCompletable(String key) {
        if (!isPresent()) {
            return observableCache.getCompletable(key);
        }
        return new CompletableFromCache(null, observableCache);
    }

    public <V> ObservableFromCache<V> thanGetObservable(String key) {
        return observableCache.getObservable(key);
    }

    public <V> ObservableFromCache<V> thanGetObservable(String key, Class<V> observableClass) {
        return observableCache.getObservable(key, observableClass);
    }

    public <V> SingleFromCache<V> thanGetSingle(String key) {
        return observableCache.getSingle(key);
    }

    public <V> SingleFromCache<V> thanGetSingle(String key, Class<V> singleClass) {
        return observableCache.getSingle(key, singleClass);
    }

    public CompletableFromCache thanGetCompletable(String key) {
        return observableCache.getCompletable(key);
    }

}
