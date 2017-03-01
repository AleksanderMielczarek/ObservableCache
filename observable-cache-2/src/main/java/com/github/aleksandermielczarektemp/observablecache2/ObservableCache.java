package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.AbstarctObservableCache;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public abstract class ObservableCache extends AbstarctObservableCache {

    public abstract <T> void cache(String key, Flowable<T> flowable);

    @Nullable
    protected abstract <T> Flowable<T> getFromCache(String key);

    public <T> Observable<T> cache(String key, Observable<T> observable) {
        Flowable<T> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
        cache(key, flowable);
        return flowable.toObservable();
    }

    public <T> Maybe<T> cache(String key, Maybe<T> maybe) {
        Flowable<T> flowable = maybe.toFlowable();
        cache(key, flowable);
        return flowable.firstElement();
    }

    public <T> Single<T> cache(String key, Single<T> single) {
        Flowable<T> flowable = single.toFlowable();
        cache(key, flowable);
        return flowable.firstOrError();
    }

    public Completable cache(String key, Completable completable) {
        Flowable<?> flowable = completable.toFlowable();
        cache(key, flowable);
        return flowable.ignoreElements();
    }

    public <T> CacheableFlowable<T> cacheFlowable(String key) {
        return new CacheableFlowable<>(key, this);
    }

    public <T> CacheableObservable<T> cacheObservable(String key) {
        return new CacheableObservable<>(key, this);
    }

    public CacheableCompletable cacheCompletable(String key) {
        return new CacheableCompletable(key, this);
    }

    public <T> CacheableMaybe<T> cacheMaybe(String key) {
        return new CacheableMaybe<>(key, this);
    }

    public <T> CacheableSingle<T> cacheSingle(String key) {
        return new CacheableSingle<>(key, this);
    }

    public <T> FlowableFromCache<T> getFlowable(String key) {
        Flowable<T> flowableFromCache = getFromCache(key);
        return new FlowableFromCache<>(flowableFromCache, this);
    }

    public <T> ObservableFromCache<T> getObservable(String key) {
        Flowable<T> flowableFromCache = getFromCache(key);
        if (flowableFromCache != null) {
            return new ObservableFromCache<>(flowableFromCache.toObservable(), this);
        }
        return new ObservableFromCache<>(null, this);
    }

    public <T> MaybeFromCache<T> getMaybe(String key) {
        Flowable<T> flowableFromCache = getFromCache(key);
        if (flowableFromCache != null) {
            return new MaybeFromCache<>(flowableFromCache.firstElement(), this);
        }
        return new MaybeFromCache<>(null, this);
    }

    public <T> SingleFromCache<T> getSingle(String key) {
        Flowable<T> flowableFromCache = getFromCache(key);
        if (flowableFromCache != null) {
            return new SingleFromCache<>(flowableFromCache.firstOrError(), this);
        }
        return new SingleFromCache<>(null, this);
    }

    public CompletableFromCache getCompletable(String key) {
        Flowable<?> flowableFromCache = getFromCache(key);
        if (flowableFromCache != null) {
            return new CompletableFromCache(flowableFromCache.ignoreElements(), this);
        }
        return new CompletableFromCache(null, this);
    }

    <T> Flowable<T> cacheFlowable(final String key, Flowable<T> flowable) {
        Flowable<T> cached = flowable.cache().doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
    }

    <T> Observable<T> cacheObservable(final String key, Observable<T> observable) {
        Flowable<T> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
        Flowable<T> cachedFlowable = cacheFlowable(key, flowable);
        return cachedFlowable.toObservable();
    }

    <T> Maybe<T> cacheMaybe(String key, Maybe<T> maybe) {
        Flowable<T> flowable = maybe.toFlowable();
        Flowable<T> cachedFlowable = cacheFlowable(key, flowable);
        return cachedFlowable.firstElement();
    }

    <T> Single<T> cacheSingle(String key, Single<T> single) {
        Flowable<T> flowable = single.toFlowable();
        Flowable<T> cachedFlowable = cacheFlowable(key, flowable);
        return cachedFlowable.firstOrError();
    }

    Completable cacheCompletable(String key, Completable completable) {
        Flowable<?> flowable = completable.toFlowable();
        Flowable<?> cachedFlowable = cacheFlowable(key, flowable);
        return cachedFlowable.ignoreElements();
    }
}
