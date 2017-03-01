package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import io.reactivex.Observable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class ObservableFromCache<T> extends ValueFromCache<Observable<T>, ObservableFromCache.ObservableInCacheAction<T>> {

    ObservableFromCache(@Nullable Observable<T> valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public ObservableFromCache<T> ifPresent(ObservableInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    public interface ObservableInCacheAction<T> extends ValueInCacheAction<Observable<T>> {
        @Override
        void action(Observable<T> observableFromCache);
    }

}
