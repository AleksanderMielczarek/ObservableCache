package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Observable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class ObservableFromCache<T> extends ValueFromCache<Observable<T>> {

    ObservableFromCache(@Nullable Observable<T> valueFromCache) {
        super(valueFromCache);
    }

}
