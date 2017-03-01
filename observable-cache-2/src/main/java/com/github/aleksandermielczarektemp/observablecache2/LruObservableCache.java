package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;
import android.util.LruCache;

import io.reactivex.Flowable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public class LruObservableCache extends ObservableCache {

    public static final int DEFAULT_CACHE_SIZE = 16;

    static volatile ObservableCache defaultInstance;

    private final LruCache<String, Flowable<?>> observables;

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
    public <T> void cache(String key, Flowable<T> flowable) {
        observables.put(key, flowable);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        observables.evictAll();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Flowable<?> observable = observables.remove(key);
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
    protected <T> Flowable<T> getFromCache(String key) {
        return (Flowable<T>) observables.get(key);
    }
}