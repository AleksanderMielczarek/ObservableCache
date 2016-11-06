package com.github.aleksandermielczarek.observablecache.service.processor.api.exception;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public class ObservableCacheServiceException extends RuntimeException {

    public ObservableCacheServiceException(String message) {
        super(message);
    }

    public ObservableCacheServiceException(String message, Throwable cause) {
        super(message, cause);

    }

}
