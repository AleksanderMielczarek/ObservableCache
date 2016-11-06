package com.github.aleksandermielczarek.observablecache.service.processor.method.extend;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class ObservableExtendMethod implements FromCacheMethod {

    @Override
    public String additionalToken() {
        return "cached";
    }

    @Override
    public String returnedBaseType() {
        return "com.github.aleksandermielczarek.observablecache.CacheableObservable";
    }

    @Override
    public String returnedType() {
        return "com.github.aleksandermielczarek.observablecache.ObservableFromCache";
    }

    @Override
    public String fromCacheMethod() {
        return "getObservable";
    }

}
