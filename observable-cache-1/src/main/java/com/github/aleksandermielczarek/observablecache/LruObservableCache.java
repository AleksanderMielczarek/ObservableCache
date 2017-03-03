package com.github.aleksandermielczarek.observablecache;


import android.support.annotation.Nullable;
import android.util.LruCache;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class LruObservableCache extends ObservableCache {

    public static final int DEFAULT_CACHE_SIZE = 16;

    private static volatile ObservableCache defaultInstance;

    private final LruCache<String, Observable<?>> observables;

    private LruObservableCache() {
        observables = new LruCache<>(DEFAULT_CACHE_SIZE);
    }

    private LruObservableCache(int size) {
        observables = new LruCache<>(size);
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
    public <T> void cache(String key, Observable<T> observable) {
        observables.put(key, observable);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        observables.evictAll();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Observable<?> observable = observables.remove(key);
        return observable != null;
    }

    @Override
    public int size() {
        return observables.size();
    }

    @Override
    public boolean exists(String key) {
        return getFromCache(key) != null;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> Observable<T> getFromCache(String key) {
        return (Observable<T>) observables.get(key);
    }
}
