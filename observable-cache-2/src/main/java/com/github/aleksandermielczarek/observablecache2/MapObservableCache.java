package com.github.aleksandermielczarek.observablecache2;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class MapObservableCache extends ObservableCache {

    private static volatile ObservableCache defaultInstance;

    private final Map<String, Flowable<?>> flowables;
    private final Map<String, Observable<?>> observables;
    private final Map<String, Single<?>> singles;
    private final Map<String, Completable> completables;
    private final Map<String, Maybe<?>> maybes;

    private MapObservableCache() {
        flowables = new HashMap<>();
        observables = new HashMap<>();
        singles = new HashMap<>();
        completables = new HashMap<>();
        maybes = new HashMap<>();
    }

    private MapObservableCache(int size) {
        flowables = new HashMap<>(size);
        observables = new HashMap<>(size);
        singles = new HashMap<>(size);
        completables = new HashMap<>(size);
        maybes = new HashMap<>(size);
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
    protected <T> void cache(String key, Flowable<T> flowable) {
        flowables.put(key, flowable);
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
    protected void cache(String key, Completable completable) {
        completables.put(key, completable);
    }

    @Override
    protected <T> void cache(String key, Maybe<T> maybe) {
        maybes.put(key, maybe);
    }

    @Override
    public boolean clear() {
        boolean empty = isEmpty();
        flowables.clear();
        observables.clear();
        singles.clear();
        completables.clear();
        maybes.clear();
        return !empty;
    }

    @Override
    public boolean remove(String key) {
        Flowable<?> removedFlowable = flowables.remove(key);
        Observable<?> removedObservable = observables.remove(key);
        Single<?> removedSingle = singles.remove(key);
        Completable removedCompletable = completables.remove(key);
        Maybe<?> removedMaybe = maybes.remove(key);
        return removedFlowable != null || removedObservable != null || removedSingle != null || removedCompletable != null || removedMaybe != null;
    }

    @Override
    public int size() {
        return flowables.size() + observables.size() + singles.size() + completables.size() + maybes.size();
    }

    @Override
    public boolean exists(String key) {
        return flowables.containsKey(key) || observables.containsKey(key) || singles.containsKey(key) || completables.containsKey(key) || maybes.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return flowables.isEmpty() && observables.isEmpty() && singles.isEmpty() && completables.isEmpty() && maybes.isEmpty();
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
    protected <T> Observable<T> getObservableFromCache(String key) {
        return (Observable<T>) observables.get(key);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Single<T> getSingleFromCache(String key) {
        return (Single<T>) singles.get(key);
    }

    @Nullable
    @Override
    protected Completable getCompletableFromCache(String key) {
        return completables.get(key);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Maybe<T> getMaybeFromCache(String key) {
        return (Maybe<T>) maybes.get(key);
    }

}
