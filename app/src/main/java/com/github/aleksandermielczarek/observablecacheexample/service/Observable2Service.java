package com.github.aleksandermielczarek.observablecacheexample.service;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Aleksander Mielczarek on 09.02.2017.
 */

public class Observable2Service {

    public static final int DELAY = 4;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public Flowable<String> flowable() {
        return Flowable.fromCallable(() -> "flowable")
                .delay(DELAY, TIME_UNIT);
    }

    public Observable<String> observable() {
        return Observable.fromCallable(() -> "observable")
                .delay(DELAY, TIME_UNIT);
    }

    public Maybe<String> maybe() {
        return Maybe.fromCallable(() -> "maybe")
                .delay(DELAY, TIME_UNIT);
    }

    public Single<String> single() {
        return Single.fromCallable(() -> "single")
                .delay(DELAY, TIME_UNIT);
    }

    public Completable completable() {
        return Completable.fromCallable(() -> null)
                .delay(DELAY, TIME_UNIT);
    }

    public Flowable<String> flowableError() {
        return flowable().doOnNext(observable -> {
            throw new IllegalArgumentException("flowableError");
        }).doOnError(throwable -> Log.e("ERROR", throwable.getMessage()));
    }

    public Observable<String> observableError() {
        return observable().doOnNext(observable -> {
            throw new IllegalArgumentException("observableError");
        }).doOnError(throwable -> Log.e("ERROR", throwable.getMessage()));
    }

    public Maybe<String> maybeError() {
        return maybe().doOnSuccess(single -> {
            throw new IllegalArgumentException("maybeError");
        }).doOnError(throwable -> Log.e("ERROR", throwable.getMessage()));
    }

    public Single<String> singleError() {
        return single().doOnSuccess(single -> {
            throw new IllegalArgumentException("singleError");
        }).doOnError(throwable -> Log.e("ERROR", throwable.getMessage()));
    }

    public Completable completableError() {
        return completable().doOnComplete(() -> {
            throw new IllegalArgumentException("completableError");
        }).doOnError(throwable -> Log.e("ERROR", throwable.getMessage()));
    }

}
