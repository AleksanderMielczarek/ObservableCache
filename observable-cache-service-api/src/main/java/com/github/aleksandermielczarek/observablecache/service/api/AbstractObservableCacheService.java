package com.github.aleksandermielczarek.observablecache.service.api;


import com.github.aleksandermielczarek.observablecache.api.AbstarctObservableCache;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public abstract class AbstractObservableCacheService<T extends AbstarctObservableCache> {

    private static final String ERROR_MESSAGE = "Cannot create ObservableCacheServiceCreatorImpl";

    private final T observableCache;
    private final ObservableCacheServiceCreator<T> observableCacheServiceCreator;

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

    public <T> T createObservableCacheService(Class<T> observableCacheServiceClass) {
        return observableCacheServiceCreator.createObservableCacheService(observableCacheServiceClass, observableCache);
    }
}
