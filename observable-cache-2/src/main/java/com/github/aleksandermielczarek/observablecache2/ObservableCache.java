package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache.api.Cache;

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

    public abstract <T> void cache(String key, Observable<T> observable);

    public abstract <T> void cache(String key, Single<T> single);

    public abstract void cache(String key, Completable completable);

    public abstract <T> void cache(String key, Maybe<T> maybe);

    @Nullable
    protected abstract <T> Flowable<T> getFlowableFromCache(String key);

    @Nullable
    protected abstract <T> Observable<T> getObservableFromCache(String key);

    @Nullable
    protected abstract <T> Single<T> getSingleFromCache(String key);

    @Nullable
    protected abstract Completable getCompletableFromCache(String key);

    @Nullable
    protected abstract <T> Maybe<T> getMaybeFromCache(String key);

    public <T> CacheableFlowable<T> cacheFlowable(String key) {
        return new CacheableFlowable<>(key, this);
    }

    public <T> CacheableObservable<T> cacheObservable(String key) {
        return new CacheableObservable<>(key, this);
    }

    public <T> CacheableSingle<T> cacheSingle(String key) {
        return new CacheableSingle<>(key, this);
    }

    public CacheableCompletable cacheCompletable(String key) {
        return new CacheableCompletable(key, this);
    }

    public <T> CacheableMaybe<T> cacheMaybe(String key) {
        return new CacheableMaybe<>(key, this);
    }

    public <T> FlowableFromCache<T> getFlowable(String key) {
        Flowable<T> flowableFromCache = getFlowableFromCache(key);
        return new FlowableFromCache<>(flowableFromCache);
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
        Completable completableFromCache = getCompletableFromCache(key);
        return new CompletableFromCache(completableFromCache);
    }

    public <T> MaybeFromCache<T> getMaybe(String key) {
        Maybe<T> maybeFromCache = getMaybeFromCache(key);
        return new MaybeFromCache<>(maybeFromCache);
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
        Observable<T> cached = observable.cache().doAfterTerminate(new Action() {
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

    Completable cacheCompletable(final String key, Completable completable) {
        Completable cached = completable.cache().doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                remove(key);
            }
        });
        cache(key, cached);
        return cached;
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

}
