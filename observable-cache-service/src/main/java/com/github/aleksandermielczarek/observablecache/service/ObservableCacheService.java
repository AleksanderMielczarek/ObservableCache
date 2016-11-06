package com.github.aleksandermielczarek.observablecache.service;

import com.github.aleksandermielczarek.observablecache.ObservableCache;
import com.github.aleksandermielczarek.observablecache.service.api.AbstractObservableCacheService;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class ObservableCacheService extends AbstractObservableCacheService<ObservableCache> {

    public ObservableCacheService(ObservableCache observableCache) {
        super(observableCache);
    }

    @Override
    public String creatorClassImpl() {
        return "com.github.aleksandermielczarek.observablecache.service.ObservableCacheServiceCreatorImpl";
    }

}
