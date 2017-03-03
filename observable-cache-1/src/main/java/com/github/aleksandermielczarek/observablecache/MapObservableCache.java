package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class MapObservableCache extends ObservableCache {

    private static volatile ObservableCache defaultInstance;

    private final Map<String, Observable<?>> observables;

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
    public <T> void cache(String key, Observable<T> observable) {
        observables.put(key, observable);
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

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> Observable<T> getFromCache(String key) {
        return (Observable<T>) observables.get(key);
    }
}
