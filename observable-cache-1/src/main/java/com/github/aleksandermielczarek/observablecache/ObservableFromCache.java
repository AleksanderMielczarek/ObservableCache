package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class ObservableFromCache<T> extends ValueFromCache<Observable<T>, ObservableFromCache.ObservableInCacheAction<T>> {

    ObservableFromCache(@Nullable Observable<T> valueFromCache) {
        super(valueFromCache);
    }

    @Override
    public void ifPresent(ObservableInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
    }

    public interface ObservableInCacheAction<T> extends ValueFromCache.ValueInCacheAction<Observable<T>> {
        @Override
        void action(Observable<T> observableFromCache);
    }

}
