package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;
import com.github.aleksandermielczarek.observablecache.api.ValueInCacheAction;

import io.reactivex.Flowable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class FlowableFromCache<T> extends ValueFromCache<Flowable<T>, FlowableFromCache.FlowableInCacheAction<T>> {

    FlowableFromCache(@Nullable Flowable<T> valueFromCache, ObservableCache observableCache) {
        super(valueFromCache, observableCache);
    }

    @Override
    public FlowableFromCache<T> ifPresent(FlowableInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
        return this;
    }

    public interface FlowableInCacheAction<T> extends ValueInCacheAction<Flowable<T>> {
        @Override
        void action(Flowable<T> flowableFromCache);
    }

}
