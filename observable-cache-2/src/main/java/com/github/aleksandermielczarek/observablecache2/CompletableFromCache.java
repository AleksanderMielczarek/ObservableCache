package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Completable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CompletableFromCache extends ValueFromCache<Completable, CompletableFromCache.CompletableInCacheAction> {

    CompletableFromCache(@Nullable Completable valueFromCache) {
        super(valueFromCache);
    }

    @Override
    public void ifPresent(CompletableInCacheAction valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
    }

    public interface CompletableInCacheAction extends ValueFromCache.ValueInCacheAction<Completable> {
        @Override
        void action(Completable completable);
    }

}
