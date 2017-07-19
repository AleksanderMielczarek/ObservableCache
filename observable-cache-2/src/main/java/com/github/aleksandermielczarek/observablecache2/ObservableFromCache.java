package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Observable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
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
