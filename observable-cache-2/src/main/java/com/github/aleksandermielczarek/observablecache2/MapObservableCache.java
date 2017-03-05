package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class MapObservableCache extends AbstractObservableCache {

    private static volatile AbstractObservableCache defaultInstance;

    private final Map<String, Flowable<?>> flowables;
    private final Map<String, Single<?>> singles;
    private final Map<String, Maybe<?>> maybes;

    private MapObservableCache() {
        flowables = new HashMap<>();
        singles = new HashMap<>();
        maybes = new HashMap<>();
    }

    private MapObservableCache(int size) {
        flowables = new HashMap<>(size);
        singles = new HashMap<>(size);
        maybes = new HashMap<>(size);
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
        return new MapObservableCache();
    }

    public static AbstractObservableCache newInstance(int size) {
        return new MapObservableCache(size);
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
        flowables.clear();
        singles.clear();
        maybes.clear();
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
        return flowables.containsKey(key) || singles.containsKey(key) || maybes.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return flowables.isEmpty() && singles.isEmpty() && maybes.isEmpty();
    }

    @Nullable
    @Override
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
