package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import rx.Single;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class SingleFromCache<T> extends ValueFromCache<Single<T>, SingleFromCache.SingleInCacheAction<T>> {

    SingleFromCache(@Nullable Single<T> valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public SingleFromCache<T> ifPresent(SingleInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    public interface SingleInCacheAction<T> extends ValueInCacheAction<Single<T>> {
        @Override
        void action(Single<T> singleFromCache);
    }

}
