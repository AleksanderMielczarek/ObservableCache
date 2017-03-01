package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class CompletableFromCache extends ValueFromCache<Completable, CompletableFromCache.CompletableInCacheAction> {

    CompletableFromCache(@Nullable Completable valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public CompletableFromCache ifPresent(CompletableInCacheAction valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    public interface CompletableInCacheAction extends ValueInCacheAction<Completable> {
        @Override
        void action(Completable completable);
    }

}
