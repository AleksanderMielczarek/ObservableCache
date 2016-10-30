package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import rx.Completable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class CompletableFromCache extends ValueFromCache<Completable, CompletableFromCache.CompletableInCacheAction, CompletableFromCache.CompletableNotInCacheAction> {

    CompletableFromCache(@Nullable Completable valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public CompletableFromCache ifPresent(CompletableInCacheAction valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    @Override
    public CompletableFromCache orElse(CompletableNotInCacheAction valueNotInCacheAction) {
        super.orElse(valueNotInCacheAction);
        return this;
    }

    public interface CompletableInCacheAction extends ValueInCacheAction<Completable> {
        @Override
        void action(Completable completable);
    }

    public interface CompletableNotInCacheAction extends ValueNotInCacheAction {

    }
}
