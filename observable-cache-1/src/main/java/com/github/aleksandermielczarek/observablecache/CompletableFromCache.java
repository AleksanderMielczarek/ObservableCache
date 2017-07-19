package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import rx.Completable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class CompletableFromCache extends ValueFromCache<Completable> {

    public CompletableFromCache() {
        this(null);
    }

    public CompletableFromCache(@Nullable Completable valueFromCache) {
        super(valueFromCache);
    }

    public static CompletableFromCache empty() {
        return new CompletableFromCache();
    }

    public static CompletableFromCache of(@Nullable Completable valueFromCache) {
        return new CompletableFromCache(valueFromCache);
    }

}
