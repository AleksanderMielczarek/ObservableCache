package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import rx.Single;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class SingleFromCache<T> extends ValueFromCache<Single<T>, SingleFromCache.SingleInCacheAction<T>, SingleFromCache.SingleNotInCacheAction> {

    SingleFromCache(@Nullable Single<T> valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public SingleFromCache<T> ifPresent(SingleInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    @Override
    public SingleFromCache<T> orElse(SingleNotInCacheAction valueNotInCacheAction) {
        super.orElse(valueNotInCacheAction);
        return this;
    }

    public interface SingleInCacheAction<T> extends ValueInCacheAction<Single<T>> {
        @Override
        void action(Single<T> singleFromCache);
    }

    public interface SingleNotInCacheAction extends ValueNotInCacheAction {

    }
}
