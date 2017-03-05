package com.github.aleksandermielczarek.observablecache.service;

import com.github.aleksandermielczarek.observablecache.AbstractObservableCache;
import com.github.aleksandermielczarek.observablecache.service.api.AbstractObservableCacheService;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public final class ObservableCacheService extends AbstractObservableCacheService<AbstractObservableCache> {

    public ObservableCacheService(AbstractObservableCache observableCache) {
        super(observableCache);
    }

    @Override
    protected String creatorClassImpl() {
        return "com.github.aleksandermielczarek.observablecache.service.ObservableCacheServiceCreatorImpl";
    }

}
