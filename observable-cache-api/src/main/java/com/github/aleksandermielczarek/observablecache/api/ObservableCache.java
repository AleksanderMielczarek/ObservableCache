package com.github.aleksandermielczarek.observablecache.api;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public interface ObservableCache {

    boolean clear();

    boolean remove(String key);

    int size();

    boolean exists(String key);

    boolean isEmpty();

}
