package com.github.aleksandermielczarek.observablecache.service.processor.method.base;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class CompletableBaseMethod implements BaseMethod {

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableCompletable";
    }

    @Override
    public String cacheMethod() {
        return "cacheCompletable";
    }

}
