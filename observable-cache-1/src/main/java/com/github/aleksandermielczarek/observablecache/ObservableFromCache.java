package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class ObservableFromCache<T> extends ValueFromCache<Observable<T>> {

    public ObservableFromCache() {
        this(null);
    }

    public ObservableFromCache(@Nullable Observable<T> valueFromCache) {
        super(valueFromCache);
    }

    public static <T> ObservableFromCache<T> empty() {
        return new ObservableFromCache<>();
    }

    public static <T> ObservableFromCache<T> of(@Nullable Observable<T> valueFromCache) {
        return new ObservableFromCache<>(valueFromCache);
    }

}
