package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.observablecache2.AbstractObservableCache;
import com.github.aleksandermielczarek.observablecacheexample.service.Cached2Service;
import com.github.aleksandermielczarek.observablecacheexample.service.Observable2Service;

import org.parceler.Parcel;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Aleksander Mielczarek on 10.02.2017.
 */

public class Observable2ViewModel {

    public static final String OBSERVABLE_CACHE_KEY_FLOWABLE = "flowable";
    public static final String OBSERVABLE_CACHE_KEY_OBSERVABLE = "observable";
    public static final String OBSERVABLE_CACHE_KEY_SINGLE = "single";
    public static final String OBSERVABLE_CACHE_KEY_MAYBE = "maybe";
    public static final String OBSERVABLE_CACHE_KEY_COMPLETABLE = "completable";
    public static final String OBSERVABLE_CACHE_KEY_FLOWABLE_ERROR = "flowableError";
    public static final String OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR = "observableError";
    public static final String OBSERVABLE_CACHE_KEY_SINGLE_ERROR = "singleError";
    public static final String OBSERVABLE_CACHE_KEY_MAYBE_ERROR = "maybeError";
    public static final String OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR = "completableError";

    public final ObservableField<String> result = new ObservableField<>();

    private final Observable2Service observable2Service;
    private final AbstractObservableCache observableCache;
    private final Cached2Service cached2Service;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public Observable2ViewModel(Observable2Service observable2Service, AbstractObservableCache observableCache, Cached2Service cached2Service) {
        this.observable2Service = observable2Service;
        this.observableCache = observableCache;
        this.cached2Service = cached2Service;
    }

    public void testFlowable() {
        testFlowable(observable2Service.flowable()
                .compose(observableCache.cacheFlowable(OBSERVABLE_CACHE_KEY_FLOWABLE)));
    }

    public void testObservable() {
        testObservable(observable2Service.observable()
                .compose(observableCache.cacheObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE)));
    }

    public void testSingle() {
        testSingle(observable2Service.single()
                .compose(observableCache.cacheSingle(OBSERVABLE_CACHE_KEY_SINGLE)));
    }

    public void testMaybe() {
        testMaybe(observable2Service.maybe()
                .compose(observableCache.cacheMaybe(OBSERVABLE_CACHE_KEY_MAYBE)));
    }

    public void testCompletable() {
        testCompletable(observable2Service.completable()
                .compose(observableCache.cacheCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE)));
    }

    public void testFlowableError() {
        testFlowable(observable2Service.flowableError()
                .compose(observableCache.cacheFlowable(OBSERVABLE_CACHE_KEY_FLOWABLE_ERROR)));
    }

    public void testObservableError() {
        testObservable(observable2Service.observableError()
                .compose(observableCache.cacheObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR)));
    }

    public void testSingleError() {
        testSingle(observable2Service.singleError()
                .compose(observableCache.cacheSingle(OBSERVABLE_CACHE_KEY_SINGLE_ERROR)));
    }

    public void testMaybeError() {
        testMaybe(observable2Service.maybeError()
                .compose(observableCache.cacheMaybe(OBSERVABLE_CACHE_KEY_MAYBE_ERROR)));
    }

    public void testCompletableError() {
        testCompletable(observable2Service.completableError()
                .compose(observableCache.cacheCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR)));
    }

    public void testServiceFlowable() {
        testFlowable(observable2Service.flowable()
                .compose(cached2Service.flowable()));
    }

    public void testServiceObservable() {
        testObservable(observable2Service.observable()
                .compose(cached2Service.observable()));
    }

    public void testServiceSingle() {
        testSingle(observable2Service.single()
                .compose(cached2Service.single()));
    }

    public void testServiceMaybe() {
        testMaybe(observable2Service.maybe()
                .compose(cached2Service.maybe()));
    }

    public void testServiceCompletable() {
        testCompletable(observable2Service.completable()
                .compose(cached2Service.completable()));
    }

    public void testServiceFlowableError() {
        testFlowable(observable2Service.flowableError()
                .compose(cached2Service.flowableError()));
    }

    public void testServiceObservableError() {
        testObservable(observable2Service.observableError()
                .compose(cached2Service.observableError()));
    }

    public void testServiceSingleError() {
        testSingle(observable2Service.singleError()
                .compose(cached2Service.singleError()));
    }

    public void testServiceMaybeError() {
        testMaybe(observable2Service.maybeError()
                .compose(cached2Service.maybeError()));
    }

    public void testServiceCompletableError() {
        testCompletable(observable2Service.completableError()
                .compose(cached2Service.completableError()));
    }

    private void testFlowable(Flowable<String> testFlowable) {
        disposables.add(testFlowable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    private void testObservable(Observable<String> testObservable) {
        disposables.add(testObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    public void testSingle(Single<String> testSingle) {
        disposables.add(testSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    public void testMaybe(Maybe<String> testMaybe) {
        disposables.add(testMaybe
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::set, throwable -> result.set(throwable.getMessage())));
    }

    public void testCompletable(Completable completable) {
        disposables.add(completable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> result.set("completable"), throwable -> result.set(throwable.getMessage())));
    }

    public void restoreObservables() {
        observableCache.<String>getFlowable(OBSERVABLE_CACHE_KEY_FLOWABLE).ifPresent(this::testFlowable);
        observableCache.<String>getObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE).ifPresent(this::testObservable);
        observableCache.<String>getSingle(OBSERVABLE_CACHE_KEY_SINGLE).ifPresent(this::testSingle);
        observableCache.<String>getMaybe(OBSERVABLE_CACHE_KEY_MAYBE).ifPresent(this::testMaybe);
        observableCache.getCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE).ifPresent(this::testCompletable);

        observableCache.<String>getFlowable(OBSERVABLE_CACHE_KEY_FLOWABLE_ERROR).ifPresent(this::testFlowable);
        observableCache.<String>getObservable(OBSERVABLE_CACHE_KEY_OBSERVABLE_ERROR).ifPresent(this::testObservable);
        observableCache.<String>getSingle(OBSERVABLE_CACHE_KEY_SINGLE_ERROR).ifPresent(this::testSingle);
        observableCache.<String>getMaybe(OBSERVABLE_CACHE_KEY_MAYBE_ERROR).ifPresent(this::testMaybe);
        observableCache.getCompletable(OBSERVABLE_CACHE_KEY_COMPLETABLE_ERROR).ifPresent(this::testCompletable);

        cached2Service.cachedFlowable().ifPresent(this::testFlowable);
        cached2Service.cachedObservable().ifPresent(this::testObservable);
        cached2Service.cachedSingle().ifPresent(this::testSingle);
        cached2Service.cachedMaybe().ifPresent(this::testMaybe);
        cached2Service.cachedCompletable().ifPresent(this::testCompletable);

        cached2Service.cachedFlowableError().ifPresent(this::testFlowable);
        cached2Service.cachedObservableError().ifPresent(this::testObservable);
        cached2Service.cachedSingleError().ifPresent(this::testSingle);
        cached2Service.cachedMaybeError().ifPresent(this::testMaybe);
        cached2Service.cachedCompletableError().ifPresent(this::testCompletable);
    }

    public void clear() {
        result.set("");
        dispose();
        observableCache.clear();
    }

    public void dispose() {
        disposables.clear();
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
