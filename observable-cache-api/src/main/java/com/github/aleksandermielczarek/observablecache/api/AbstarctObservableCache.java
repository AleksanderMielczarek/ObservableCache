package com.github.aleksandermielczarek.observablecache.api;

/**
 * Created by Aleksander Mielczarek on 29.10.2016.
 */

public abstract class AbstarctObservableCache {

    public abstract boolean clear();

    public abstract boolean remove(String key);

    public abstract int size();

    public abstract boolean exists(String key);

    public boolean isEmpty() {
        return size() == 0;
    }

}
