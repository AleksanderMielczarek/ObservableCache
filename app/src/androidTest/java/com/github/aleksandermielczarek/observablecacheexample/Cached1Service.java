package com.github.aleksandermielczarek.observablecacheexample;

import com.github.aleksandermielczarek.observablecache.CacheableCompletable;
import com.github.aleksandermielczarek.observablecache.CacheableObservable;
import com.github.aleksandermielczarek.observablecache.CacheableSingle;
import com.github.aleksandermielczarek.observablecache.CompletableFromCache;
import com.github.aleksandermielczarek.observablecache.ObservableFromCache;
import com.github.aleksandermielczarek.observablecache.SingleFromCache;
import com.github.aleksandermielczarek.observablecache.service.annotations.ObservableCacheService;

/**
 * Created by Aleksander Mielczarek on 05.03.2017.
 */

@ObservableCacheService
public interface Cached1Service {

    CacheableObservable<String> observable();

    ObservableFromCache<String> cachedObservable();

    CacheableSingle<String> single();

    SingleFromCache<String> cachedSingle();

    CacheableCompletable completable();

    CompletableFromCache cachedCompletable();

}
