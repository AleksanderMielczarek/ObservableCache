package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public class MapObservableCache extends ObservableCache {

    static volatile ObservableCache defaultInstance;

    private final Map<String, Flowable<?>> observables;

    private MapObservableCache() {
        observables = new HashMap<>();
    }

    private MapObservableCache(int size) {
        observables = new HashMap<>(size);
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
        return new MapObservableCache();
    }

    public static ObservableCache newInstance(int size) {
        return new MapObservableCache(size);
    }

    @Override
    public <T> void cache(String key, Flowable<T> flowable) {
        observables.put(key, flowable);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        observables.clear();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        return observables.remove(key) != null;
    }

    @Override
    public int size() {
        return observables.size();
    }

    @Override
    public boolean exists(String key) {
        return observables.containsKey(key);
    }

    @Nullable
    @Override
    protected <T> Flowable<T> getFromCache(String key) {
        return (Flowable<T>) observables.get(key);
    }

}
