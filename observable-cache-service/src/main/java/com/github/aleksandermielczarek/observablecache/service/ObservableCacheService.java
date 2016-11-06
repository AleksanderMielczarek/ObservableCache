package com.github.aleksandermielczarek.observablecache.service;

import com.github.aleksandermielczarek.observablecache.ObservableCache;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public class ObservableCacheService {

    private static final String ERROR_MESSAGE = "Cannot create ObservableCacheServiceCreatorImpl";
    private static final String CREATOR_CLASS_IMPL = "com.github.aleksandermielczarek.observablecache.service.ObservableCacheServiceCreatorImpl";

    private final ObservableCache observableCache;
    private final ObservableCacheServiceCreator observableCacheServiceCreator;

    public ObservableCacheService(ObservableCache observableCache) {
        this.observableCache = observableCache;
        try {
            Class<?> observableCacheServiceCreatorClass = Class.forName(CREATOR_CLASS_IMPL);
            observableCacheServiceCreator = (ObservableCacheServiceCreator) observableCacheServiceCreatorClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        } catch (InstantiationException e) {
            throw new IllegalStateException(ERROR_MESSAGE, e);
        }
    }

    public <T> T createObservableCacheService(Class<T> observableCacheServiceClass) {
        return observableCacheServiceCreator.createObservableCacheService(observableCacheServiceClass, observableCache);
    }
}
