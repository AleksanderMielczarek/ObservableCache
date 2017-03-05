package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import rx.Single;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */
public final class SingleFromCache<T> extends ValueFromCache<Single<T>> {

    SingleFromCache(@Nullable Single<T> valueFromCache) {
        super(valueFromCache);
    }

}
