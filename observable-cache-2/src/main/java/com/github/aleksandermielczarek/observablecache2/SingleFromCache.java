package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Single;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class SingleFromCache<T> extends ValueFromCache<Single<T>> {

    SingleFromCache(@Nullable Single<T> valueFromCache) {
        super(valueFromCache);
    }

}
