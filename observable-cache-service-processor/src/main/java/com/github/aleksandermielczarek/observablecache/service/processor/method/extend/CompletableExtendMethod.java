package com.github.aleksandermielczarek.observablecache.service.processor.method.extend;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class CompletableExtendMethod implements FromCacheMethod {

    @Override
    public String additionalToken() {
        return "cached";
    }

    @Override
    public String returnedBaseType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableCompletable";
    }

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.CompletableFromCache";
    }

    @Override
    public String fromCacheMethod() {
        return "getCompletable";
    }

}
