package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.Cache;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.functions.Action0;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public abstract class ObservableCache implements Cache {

    protected abstract <T> void cache(String key, Observable<T> observable);

    protected abstract <T> void cache(String key, Single<T> single);

    @Nullable
    protected abstract <T> Observable<T> getObservableFromCache(String key);

    @Nullable
    protected abstract <T> Single<T> getSingleFromCache(String key);

    private void cache(String key, Completable completable) {
        Observable<?> observable = completable.toObservable();
        cache(key, observable);
    }

    public <T> CacheableObservable<T> cacheObservable(String key) {
        return new CacheableObservable<>(key, this);
    }

    public CacheableCompletable cacheCompletable(String key) {
        return new CacheableCompletable(key, this);
    }

    public <T> CacheableSingle<T> cacheSingle(String key) {
        return new CacheableSingle<>(key, this);
    }

    public <T> ObservableFromCache<T> getObservable(String key) {
        Observable<T> observableFromCache = getObservableFromCache(key);
        return new ObservableFromCache<>(observableFromCache);
    }

    public <T> SingleFromCache<T> getSingle(String key) {
        Single<T> singleFromCache = getSingleFromCache(key);
        return new SingleFromCache<>(singleFromCache);
    }

    public CompletableFromCache getCompletable(String key) {
        Observable<?> observableFromCache = getObservableFromCache(key);
        if (observableFromCache != null) {
            return new CompletableFromCache(observableFromCache.toCompletable());
        }
        return new CompletableFromCache(null);
    }

    <T> Observable<T> cacheObservable(final String key, Observable<T> observable) {
        Observable<T> cached = observable.cache().doOnTerminate(new Action0() {
            @Override
            public void call() {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
    }

    <T> Single<T> cacheSingle(final String key, Single<T> single) {
        Single<T> cached = single.cache().doAfterTerminate(new Action0() {
            @Override
            public void call() {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
    }

    Completable cacheCompletable(String key, Completable completable) {
        Observable<?> observable = completable.toObservable();
        Observable<?> cachedObservable = cacheObservable(key, observable);
        return cachedObservable.toCompletable();
    }
}
