package com.github.aleksandermielczarek.observablecache2.service.processor.method.extend;

import com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend.FromCacheMethod;

/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */
public class SingleExtendMethod implements FromCacheMethod {

    @Override
    public String additionalToken() {
        return "cached";
    }

    @Override
    public String returnedBaseType() {
        return "com.github.aleksandermielczarek.observablecache2.CacheableSingle";
    }

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache2.SingleFromCache";
    }

    @Override
    public String fromCacheMethod() {
        return "getSingle";
    }

}
