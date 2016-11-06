package com.github.aleksandermielczarek.observablecache.api;

import android.support.annotation.Nullable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public abstract class ValueFromCache<T, S extends ValueInCacheAction<T>> {

    @Nullable
    private final T valueFromCache;
    private final AbstarctObservableCache observableCache;

    protected ValueFromCache(@Nullable T valueFromCache, AbstarctObservableCache observableCache) {
        this.valueFromCache = valueFromCache;
        this.observableCache = observableCache;
    }

    public boolean isPresent() {
        return valueFromCache != null;
    }

    @Nullable
    public T get() {
        return valueFromCache;
    }

    public ValueFromCache<T, S> ifPresent(S valueInCacheAction) {
        if (isPresent()) {
            valueInCacheAction.action(valueFromCache);
        }
        return this;
    }

}
