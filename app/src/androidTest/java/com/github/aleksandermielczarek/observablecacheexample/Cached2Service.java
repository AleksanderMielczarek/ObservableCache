package com.github.aleksandermielczarek.observablecacheexample;


import com.github.aleksandermielczarek.observablecache2.CacheableCompletable;
import com.github.aleksandermielczarek.observablecache2.CacheableFlowable;
import com.github.aleksandermielczarek.observablecache2.CacheableMaybe;
import com.github.aleksandermielczarek.observablecache2.CacheableObservable;
import com.github.aleksandermielczarek.observablecache2.CacheableSingle;
import com.github.aleksandermielczarek.observablecache2.CompletableFromCache;
import com.github.aleksandermielczarek.observablecache2.FlowableFromCache;
import com.github.aleksandermielczarek.observablecache2.MaybeFromCache;
import com.github.aleksandermielczarek.observablecache2.ObservableFromCache;
import com.github.aleksandermielczarek.observablecache2.SingleFromCache;
import com.github.aleksandermielczarek.observablecache2.service.annotations.ObservableCacheService;

/**
 * Created by Aleksander Mielczarek on 10.02.2017.
 */

@ObservableCacheService
public interface Cached2Service {

    CacheableFlowable<String> flowable();

    FlowableFromCache<String> cachedFlowable();

    CacheableObservable<String> observable();

    ObservableFromCache<String> cachedObservable();

    CacheableSingle<String> single();

    SingleFromCache<String> cachedSingle();

    CacheableMaybe<String> maybe();

    MaybeFromCache<String> cachedMaybe();

    CacheableCompletable completable();

    CompletableFromCache cachedCompletable();

}
