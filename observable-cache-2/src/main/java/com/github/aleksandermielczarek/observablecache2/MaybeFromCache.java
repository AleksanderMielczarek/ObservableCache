package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.ValueFromCache;

import io.reactivex.Maybe;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class MaybeFromCache<T> extends ValueFromCache<Maybe<T>, MaybeFromCache.MaybeInCacheAction<T>> {

    MaybeFromCache(@Nullable Maybe<T> valueFromCache) {
        super(valueFromCache);
    }

    @Override
    public void ifPresent(MaybeInCacheAction<T> valueInCacheAction) {
        super.ifPresent(valueInCacheAction);
    }

    public interface MaybeInCacheAction<T> extends ValueFromCache.ValueInCacheAction<Maybe<T>> {
        @Override
        void action(Maybe<T> maybeFromCache);
    }

}
