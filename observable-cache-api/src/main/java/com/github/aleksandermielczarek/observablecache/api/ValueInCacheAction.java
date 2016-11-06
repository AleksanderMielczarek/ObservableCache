package com.github.aleksandermielczarek.observablecache.api;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public interface ValueInCacheAction<T> {
    void action(T valueFromCache);
}