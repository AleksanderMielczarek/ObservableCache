package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Completable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
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
