package com.github.aleksandermielczarek.observablecacheexample.module;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecache2.LruObservableCache;
import com.github.aleksandermielczarek.observablecache2.ObservableCache;
import com.github.aleksandermielczarek.observablecache2.service.ObservableCacheService;
import com.github.aleksandermielczarek.observablecacheexample.service.Cached2Service;
import com.github.aleksandermielczarek.observablecacheexample.service.Observable2Service;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */
@Module
@AppScope
public class Observable2Module {

    @Provides
    @AppScope
    ObservableCache provideObservableCache() {
        return LruObservableCache.newInstance();
    }

    @Provides
    @AppScope
    Observable2Service provideObservableService() {
        return new Observable2Service();
    }

    @Provides
    @AppScope
    ObservableCacheService provideObservableCacheService(ObservableCache observableCache) {
        return new ObservableCacheService(observableCache);
    }

    @Provides
    @AppScope
    Cached2Service provideCachedService(ObservableCacheService observableCacheService) {
        return observableCacheService.createObservableCacheService(Cached2Service.class);
    }
}
