package com.github.aleksandermielczarek.observablecacheexample.service;

import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public class ObservableService {

    public static final int DELAY = 4;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public Observable<String> observable() {
        return Observable.fromCallable(() -> "observable")
                .delay(15, TIME_UNIT);
    }

    public Single<String> single() {
        return Single.fromCallable(() -> "single")
                .delay(DELAY, TIME_UNIT);
    }

    public Completable completable() {
        return Completable.fromCallable(() -> null)
                .delay(DELAY, TIME_UNIT);
    }

    public Observable<String> observableError() {
        return observable().doOnNext(observable -> {
            throw new IllegalArgumentException("observableError");
        });
    }

    public Single<String> singleError() {
        return single().doOnSuccess(single -> {
            throw new IllegalArgumentException("singleError");
        });
    }

    public Completable completableError() {
        return completable().doOnEach(completable -> {
            throw new IllegalArgumentException("completableError");
        });
    }

}
