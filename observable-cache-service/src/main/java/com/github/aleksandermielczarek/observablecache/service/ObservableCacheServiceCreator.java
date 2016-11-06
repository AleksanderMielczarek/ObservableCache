package com.github.aleksandermielczarek.observablecache.service;

import com.github.aleksandermielczarek.observablecache.ObservableCache;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

interface ObservableCacheServiceCreator {

    <T> T createObservableCacheService(Class<T> observableCacheServiceClass, ObservableCache observableCache);
}
