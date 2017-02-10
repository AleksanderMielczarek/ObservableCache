package com.github.aleksandermielczarek.observablecache2.service.processor.method.base;

import com.github.aleksandermielczarek.observablecache.service.processor.api.method.base.BaseMethod;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */
public class MaybeBaseMethod implements BaseMethod {

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache2.CacheableMaybe";
    }

    @Override
    public String cacheMethod() {
        return "cacheMaybe";
    }

}
