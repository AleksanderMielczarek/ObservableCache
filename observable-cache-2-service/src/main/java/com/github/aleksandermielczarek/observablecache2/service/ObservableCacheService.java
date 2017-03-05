package com.github.aleksandermielczarek.observablecache2.service;

import com.github.aleksandermielczarek.observablecache2.AbstractObservableCache;
import com.github.aleksandermielczarek.observablecache.service.api.AbstractObservableCacheService;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public final class ObservableCacheService extends AbstractObservableCacheService<AbstractObservableCache> {

    public ObservableCacheService(AbstractObservableCache observableCache) {
        super(observableCache);
    }

    @Override
    protected String creatorClassImpl() {
        return "com.github.aleksandermielczarek.observablecache2.service.ObservableCacheServiceCreatorImpl";
    }

}
