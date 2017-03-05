package com.github.aleksandermielczarek.observablecacheexample.module;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecache.AbstractObservableCache;
import com.github.aleksandermielczarek.observablecache.LruObservableCache;
import com.github.aleksandermielczarek.observablecache.service.ObservableCacheService;
import com.github.aleksandermielczarek.observablecacheexample.service.Cached1Service;
import com.github.aleksandermielczarek.observablecacheexample.service.Observable1Service;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@Module
@AppScope
public class Observable1Module {

    @Provides
    @AppScope
    AbstractObservableCache provideObservableCache() {
        return LruObservableCache.newInstance();
    }

    @Provides
    @AppScope
    Observable1Service provideObservableService() {
        return new Observable1Service();
    }

    @Provides
    @AppScope
    ObservableCacheService provideObservableCacheService(AbstractObservableCache observableCache) {
        return new ObservableCacheService(observableCache);
    }

    @Provides
    @AppScope
    Cached1Service provideCachedService(ObservableCacheService observableCacheService) {
        return observableCacheService.createObservableCacheService(Cached1Service.class);
    }
}
