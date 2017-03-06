package com.github.aleksandermielczarek.observablecache;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Single;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class MapObservableCache extends ObservableCache {

    private static volatile ObservableCache defaultInstance;

    private final Map<String, Observable<?>> observables;
    private final Map<String, Single<?>> singles;

    private MapObservableCache() {
        observables = new HashMap<>();
        singles = new HashMap<>();
    }

    private MapObservableCache(int size) {
        observables = new HashMap<>(size);
        singles = new HashMap<>(size);
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
        observables.clear();
        singles.clear();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Observable<?> removedObservable = observables.remove(key);
        Single<?> removedSingle = singles.remove(key);
        return removedObservable != null || removedSingle != null;
    }

    @Override
    public int size() {
        return observables.size() + singles.size();
    }

    @Override
    public boolean exists(String key) {
        return observables.containsKey(key) || singles.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return observables.isEmpty() && singles.isEmpty();
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
