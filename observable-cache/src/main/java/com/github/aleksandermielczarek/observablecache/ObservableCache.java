package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.functions.Action0;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public abstract class ObservableCache {

    public abstract <T> void cache(String key, Observable<T> observable);

    public abstract boolean clear();

    public abstract boolean remove(String key);

    public abstract int size();

    public abstract boolean exists(String key);

    @Nullable
    protected abstract <T> Observable<T> getFromCache(String key);

    public <T> Single<T> cache(String key, Single<T> single) {
        Observable<T> observable = single.toObservable();
        cache(key, observable);
        return observable.toSingle();
    }

    public Completable cache(String key, Completable completable) {
        Observable<?> observable = completable.toObservable();
        cache(key, observable);
        return observable.toCompletable();
    }

    public boolean isEmpty() {
        return size() == 0;
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
        Observable<T> observableFromCache = getFromCache(key);
        return new ObservableFromCache<>(observableFromCache, this);
    }

    public <T> SingleFromCache<T> getSingle(String key) {
        Observable<T> observableFromCache = getFromCache(key);
        if (observableFromCache != null) {
            return new SingleFromCache<>(observableFromCache.toSingle(), this);
        }
        return new SingleFromCache<>(null, this);
    }

    public CompletableFromCache getCompletable(String key) {
        Observable<?> observableFromCache = getFromCache(key);
        if (observableFromCache != null) {
            return new CompletableFromCache(observableFromCache.toCompletable(), this);
        }
        return new CompletableFromCache(null, this);
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

    <T> Single<T> cacheSingle(String key, Single<T> single) {
        Observable<T> observable = single.toObservable();
        Observable<T> cachedObservable = cacheObservable(key, observable);
        return cachedObservable.toSingle();
    }

    Completable cacheCompletable(String key, Completable completable) {
        Observable<?> observable = completable.toObservable();
        Observable<?> cachedObservable = cacheObservable(key, observable);
        return cachedObservable.toCompletable();
    }
}
