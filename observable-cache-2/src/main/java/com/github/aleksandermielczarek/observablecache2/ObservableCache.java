package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.Cache;

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

public abstract class ObservableCache implements Cache {

    public abstract <T> void cache(String key, Flowable<T> flowable);

    public abstract <T> void cache(String key, Single<T> single);

    public abstract <T> void cache(String key, Maybe<T> maybe);

    @Nullable
    protected abstract <T> Flowable<T> getFlowableFromCache(String key);

    @Nullable
    protected abstract <T> Single<T> getSingleFromCache(String key);

    @Nullable
    protected abstract <T> Maybe<T> getMaybeFromCache(String key);

    public <T> Observable<T> cache(String key, Observable<T> observable) {
        Flowable<T> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
        cache(key, flowable);
        return flowable.toObservable();
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
        Flowable<T> flowableFromCache = getFlowableFromCache(key);
        return new FlowableFromCache<>(flowableFromCache);
    }

    public <T> ObservableFromCache<T> getObservable(String key) {
        Flowable<T> flowableFromCache = getFlowableFromCache(key);
        if (flowableFromCache != null) {
            return new ObservableFromCache<>(flowableFromCache.toObservable());
        }
        return new ObservableFromCache<>(null);
    }

    public <T> MaybeFromCache<T> getMaybe(String key) {
        Maybe<T> maybeFromCache = getMaybeFromCache(key);
        return new MaybeFromCache<>(maybeFromCache);
    }

    public <T> SingleFromCache<T> getSingle(String key) {
        Single<T> singleFromCache = getSingleFromCache(key);
        return new SingleFromCache<>(singleFromCache);
    }

    public CompletableFromCache getCompletable(String key) {
        Flowable<?> flowableFromCache = getFlowableFromCache(key);
        if (flowableFromCache != null) {
            return new CompletableFromCache(flowableFromCache.ignoreElements());
        }
        return new CompletableFromCache(null);
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

    <T> Maybe<T> cacheMaybe(final String key, Maybe<T> maybe) {
        Maybe<T> cached = maybe.cache().doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
    }

    <T> Single<T> cacheSingle(final String key, Single<T> single) {
        Single<T> cached = single.cache().doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
    }

    Completable cacheCompletable(String key, Completable completable) {
        Flowable<?> flowable = completable.toFlowable();
        Flowable<?> cachedFlowable = cacheFlowable(key, flowable);
        return cachedFlowable.ignoreElements();
    }
}
