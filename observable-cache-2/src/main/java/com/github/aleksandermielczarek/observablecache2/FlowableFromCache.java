package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Flowable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class FlowableFromCache<T> extends ValueFromCache<Flowable<T>> {

    FlowableFromCache(@Nullable Flowable<T> valueFromCache) {
        super(valueFromCache);
    }

}
