package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.napkin.qualifier.ActivityContext;
import com.github.aleksandermielczarek.observablecache.ObservableCache;
import com.github.aleksandermielczarek.observablecacheexample.service.Cached1Service;
import com.github.aleksandermielczarek.observablecacheexample.service.Observable1Service;

import org.parceler.Parcel;

import javax.inject.Inject;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public class Observable1ViewModel {

    public static final String OBSERVABLE_CACHE_KEY_OBSERVABLE = "observable";
    public static final String OBSERVABLE_CACHE_KEY_SINGLE = "single";
    public static final String OBSERVABLE_CACHE_KEY_COMPLETABLE = "completable";
    public static final String OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR = "observableError";
    public static final String OBSERVABLE_CACHE_KEY_SINGLE_ERROR = "singleError";
    public static final String OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR = "completableError";

    public final ObservableField<String> result = new ObservableField<>();

    private final Observable1Service observable1Service;
    private final ObservableCache observableCache;
    private final Cached1Service cached1Service;
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final Context context;

    @Inject
    public Observable1ViewModel(Observable1Service observable1Service, ObservableCache observableCache, Cached1Service cached1Service, @ActivityContext Context context) {
        this.observable1Service = observable1Service;
        this.observableCache = observableCache;
        this.cached1Service = cached1Service;
        this.context = context;
    }

    public void startObservable2Example() {
        Observable2Activity_.intent(context)
                .start();
    }

    public void testObservable() {
        testObservable(observable1Service.observable()
                .compose(observableCache.cacheObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE)));
    }

    public void testSingle() {
        testSingle(observable1Service.single()
                .compose(observableCache.cacheSingle(OBSERVABLE_CACHE_KEY_SINGLE)));
    }

    public void testCompletable() {
        testCompletable(observable1Service.completable()
                .compose(observableCache.cacheCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE)));
    }

    public void testObservableError() {
        testObservable(observable1Service.observableError()
                .compose(observableCache.cacheObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR)));
    }

    public void testSingleError() {
        testSingle(observable1Service.singleError()
                .compose(observableCache.cacheSingle(OBSERVABLE_CACHE_KEY_SINGLE_ERROR)));
    }

    public void testCompletableError() {
        testCompletable(observable1Service.completableError()
                .compose(observableCache.cacheCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR)));
    }

    public void testServiceObservable() {
        testObservable(observable1Service.observable()
                .compose(cached1Service.observable()));
    }

    public void testServiceSingle() {
        testSingle(observable1Service.single()
                .compose(cached1Service.single()));
    }

    public void testServiceCompletable() {
        testCompletable(observable1Service.completable()
                .compose(cached1Service.completable()));
    }

    public void testServiceObservableError() {
        testObservable(observable1Service.observableError()
                .compose(cached1Service.observableError()));
    }

    public void testServiceSingleError() {
        testSingle(observable1Service.singleError()
                .compose(cached1Service.singleError()));
    }

    public void testServiceCompletableError() {
        testCompletable(observable1Service.completableError()
                .compose(cached1Service.completableError()));
    }

    private void testObservable(Observable<String> testObservable) {
        subscriptions.add(testObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    public void testSingle(Single<String> testSingle) {
        subscriptions.add(testSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    public void testCompletable(Completable completable) {
        subscriptions.add(completable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> result.set("completable"), throwable -> result.set(throwable.getMessage())));
    }

    public void restoreObservables() {
        observableCache.<String>getObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE).ifPresent(this::testObservable);
        observableCache.<String>getSingle(OBSERVABLE_CACHE_KEY_SINGLE).ifPresent(this::testSingle);
        observableCache.getCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE).ifPresent(this::testCompletable);
        observableCache.<String>getObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR).ifPresent(this::testObservable);
        observableCache.<String>getSingle(OBSERVABLE_CACHE_KEY_SINGLE_ERROR).ifPresent(this::testSingle);
        observableCache.getCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR).ifPresent(this::testCompletable);

        cached1Service.cachedObservable().ifPresent(this::testObservable);
        cached1Service.cachedSingle().ifPresent(this::testSingle);
        cached1Service.cachedCompletable().ifPresent(this::testCompletable);
        cached1Service.cachedObservableError().ifPresent(this::testObservable);
        cached1Service.cachedSingleError().ifPresent(this::testSingle);
        cached1Service.cachedCompletableError().ifPresent(this::testCompletable);
    }

    public void clear() {
        result.set("");
        unsubscribe();
        observableCache.clear();
    }

    public void unsubscribe() {
        subscriptions.clear();
    }

    public State saveState() {
        return new State(result.get());
    }

    public void restoreState(@Nullable State state) {
        if (state != null) {
            result.set(state.getResult());
        }
    }

    @Parcel
    public static class State {

        String result;

        public State() {

        }

        public State(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

}
