package com.github.aleksandermielczarek.observablecache.service.processor.method.base;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class ObservableBaseMethod implements BaseMethod {

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableObservable";
    }

    @Override
    public String cacheMethod() {
        return "cacheObservable";
    }

}
