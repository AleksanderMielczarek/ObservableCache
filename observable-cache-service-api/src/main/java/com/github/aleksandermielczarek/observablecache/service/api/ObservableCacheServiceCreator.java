package com.github.aleksandermielczarek.observablecache.service.api;


import com.github.aleksandermielczarek.observablecache.api.Cache;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public interface ObservableCacheServiceCreator<T extends Cache> {

    <S> S createObservableCacheService(Class<S> observableCacheServiceClass, T observableCache);
}
