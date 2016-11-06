package com.github.aleksandermielczarek.observablecacheexample.module;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecache.LruObservableCache;
import com.github.aleksandermielczarek.observablecache.ObservableCache;
import com.github.aleksandermielczarek.observablecache.service.ObservableCacheService;
import com.github.aleksandermielczarek.observablecacheexample.service.CachedService;
import com.github.aleksandermielczarek.observablecacheexample.service.ObservableService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@Module
@AppScope
public class AppModule {

    @Provides
    @AppScope
    ObservableCache provideObservableCache() {
        return LruObservableCache.newInstance();
    }

    @Provides
    @AppScope
    ObservableService provideObservableService() {
        return new ObservableService();
    }

    @Provides
    @AppScope
    ObservableCacheService provideObservableCacheService(ObservableCache observableCache) {
        return new ObservableCacheService(observableCache);
    }

    @Provides
    @AppScope
    CachedService provideCachedService(ObservableCacheService observableCacheService) {
        return observableCacheService.createObservableCacheService(CachedService.class);
    }
}
