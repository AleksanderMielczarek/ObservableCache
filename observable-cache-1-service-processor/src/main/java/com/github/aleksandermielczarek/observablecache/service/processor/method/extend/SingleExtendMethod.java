package com.github.aleksandermielczarek.observablecache.service.processor.method.extend;

import com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend.FromCacheMethod;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class SingleExtendMethod implements FromCacheMethod {

    @Override
    public String additionalToken() {
        return "cached";
    }

    @Override
    public String returnedBaseType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableSingle";
    }

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.SingleFromCache";
    }

    @Override
    public String fromCacheMethod() {
        return "getSingle";
    }

}
