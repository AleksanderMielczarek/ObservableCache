package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import io.reactivex.Single;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
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
