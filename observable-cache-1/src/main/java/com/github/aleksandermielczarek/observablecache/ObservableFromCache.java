package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
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
