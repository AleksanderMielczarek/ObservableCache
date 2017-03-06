package com.github.aleksandermielczarek.observablecache;


import android.support.annotation.Nullable;
import android.util.LruCache;

import rx.Observable;
import rx.Single;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class LruObservableCache extends ObservableCache {

    public static final int DEFAULT_CACHE_SIZE = 16;

    private static volatile ObservableCache defaultInstance;

    private final LruCache<String, Observable<?>> observables;
    private final LruCache<String, Single<?>> singles;

    private LruObservableCache() {
        observables = new LruCache<>(DEFAULT_CACHE_SIZE);
        singles = new LruCache<>(DEFAULT_CACHE_SIZE);
    }

    private LruObservableCache(int size) {
        observables = new LruCache<>(size);
        singles = new LruCache<>(size);
    }

    public static ObservableCache getDefault() {
        if (defaultInstance == null) {
            synchronized (ObservableCache.class) {
                if (defaultInstance == null) {
                    defaultInstance = newInstance();
                }
            }
        }
        return defaultInstance;
    }

    public static ObservableCache newInstance() {
        return new LruObservableCache();
    }

    public static ObservableCache newInstance(int size) {
        return new LruObservableCache(size);
    }

    @Override
    protected <T> void cache(String key, Observable<T> observable) {
        observables.put(key, observable);
    }

    @Override
    protected <T> void cache(String key, Single<T> single) {
        singles.put(key, single);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        observables.evictAll();
        singles.evictAll();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Observable<?> observable = observables.remove(key);
        Single<?> removedSingle = singles.remove(key);
        return observable != null || removedSingle != null;
    }

    @Override
    public int size() {
        return observables.size() + singles.size();
    }

    @Override
    public boolean exists(String key) {
        return observables.get(key) != null || singles.get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> Observable<T> getObservableFromCache(String key) {
        return (Observable<T>) observables.get(key);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Single<T> getSingleFromCache(String key) {
        return (Single<T>) singles.get(key);
    }
}
