package com.github.aleksandermielczarek.observablecache.service.api;


import com.github.aleksandermielczarek.observablecache.api.ObservableCache;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public abstract class AbstractObservableCacheService<T extends ObservableCache> {

    private static final String ERROR_MESSAGE = "Cannot create ObservableCacheServiceCreatorImpl";

    private final T observableCache;
    private final ObservableCacheServiceCreator<T> observableCacheServiceCreator;

    @SuppressWarnings("TryWithIdenticalCatches")
    public AbstractObservableCacheService(T observableCache) {
        this.observableCache = observableCache;
        try {
            Class<?> observableCacheServiceCreatorClass = Class.forName(creatorClassImpl());
            observableCacheServiceCreator = (ObservableCacheServiceCreator<T>) observableCacheServiceCreatorClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        } catch (InstantiationException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        }
    }

    protected abstract String creatorClassImpl();

    public <S> S createObservableCacheService(Class<S> observableCacheServiceClass) {
        return observableCacheServiceCreator.createObservableCacheService(observableCacheServiceClass, observableCache);
    }
}
