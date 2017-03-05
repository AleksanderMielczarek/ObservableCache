package com.github.aleksandermielczarek.observablecache.api;

import android.support.annotation.Nullable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public abstract class ValueFromCache<T, S extends ValueFromCache.ValueInCacheAction<T>> {

    @Nullable
    private final T valueFromCache;

    protected ValueFromCache(@Nullable T valueFromCache) {
        this.valueFromCache = valueFromCache;
    }

    public boolean isPresent() {
        return valueFromCache != null;
    }

    @Nullable
    public T get() {
        return valueFromCache;
    }

    public void ifPresent(S valueInCacheAction) {
        if (isPresent()) {
            valueInCacheAction.action(valueFromCache);
        }
    }

    public interface ValueInCacheAction<T> {
        void action(T valueFromCache);
    }

}
