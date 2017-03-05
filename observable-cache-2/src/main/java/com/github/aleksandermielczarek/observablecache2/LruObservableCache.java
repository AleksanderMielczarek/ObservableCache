package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;
import android.util.LruCache;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class LruObservableCache extends AbstractObservableCache {

    public static final int DEFAULT_CACHE_SIZE = 16;

    private static volatile AbstractObservableCache defaultInstance;

    private final LruCache<String, Flowable<?>> flowables;
    private final LruCache<String, Single<?>> singles;
    private final LruCache<String, Maybe<?>> maybes;

    private LruObservableCache() {
        flowables = new LruCache<>(DEFAULT_CACHE_SIZE);
        singles = new LruCache<>(DEFAULT_CACHE_SIZE);
        maybes = new LruCache<>(DEFAULT_CACHE_SIZE);
    }

    private LruObservableCache(int size) {
        flowables = new LruCache<>(size);
        singles = new LruCache<>(size);
        maybes = new LruCache<>(size);
    }

    public static AbstractObservableCache getDefault() {
        if (defaultInstance == null) {
            synchronized (AbstractObservableCache.class) {
                if (defaultInstance == null) {
                    defaultInstance = newInstance();
                }
            }
        }
        return defaultInstance;
    }

    public static AbstractObservableCache newInstance() {
        return new LruObservableCache();
    }

    public static AbstractObservableCache newInstance(int size) {
        return new LruObservableCache(size);
    }

    @Override
    public <T> void cache(String key, Flowable<T> flowable) {
        flowables.put(key, flowable);
    }

    @Override
    public <T> void cache(String key, Single<T> single) {
        singles.put(key, single);
    }

    @Override
    public <T> void cache(String key, Maybe<T> maybe) {
        maybes.put(key, maybe);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        flowables.evictAll();
        singles.evictAll();
        maybes.evictAll();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Flowable<?> removedFlowable = flowables.remove(key);
        Single<?> removedSingle = singles.remove(key);
        Maybe<?> removedMaybe = maybes.remove(key);
        return removedFlowable != null || removedSingle != null || removedMaybe != null;
    }

    @Override
    public int size() {
        return flowables.size() + singles.size() + maybes.size();
    }

    @Override
    public boolean exists(String key) {
        return flowables.get(key) != null || singles.get(key) != null || maybes.get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> Flowable<T> getFlowableFromCache(String key) {
        return (Flowable<T>) flowables.get(key);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Single<T> getSingleFromCache(String key) {
        return (Single<T>) singles.get(key);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Maybe<T> getMaybeFromCache(String key) {
        return (Maybe<T>) maybes.get(key);
    }
}