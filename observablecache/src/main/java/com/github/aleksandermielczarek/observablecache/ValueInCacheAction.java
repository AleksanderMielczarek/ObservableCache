package com.github.aleksandermielczarek.observablecache;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

interface ValueInCacheAction<T> {
    void action(T valueFromCache);
}