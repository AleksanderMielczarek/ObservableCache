package com.github.aleksandermielczarek.observablecache.service.processor.method.base;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class SingleBaseMethod implements BaseMethod {

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableSingle";
    }

    @Override
    public String cacheMethod() {
        return "cacheSingle";
    }

}
