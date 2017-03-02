package com.github.aleksandermielczarek.observablecacheexample;

import android.support.test.runner.AndroidJUnit4;

import com.github.aleksandermielczarek.observablecache2.LruObservableCache;
import com.github.aleksandermielczarek.observablecache2.ObservableCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Aleksander Mielczarek on 02.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class LruObservableCache2Test {

    private final ObservableCache observableCache = LruObservableCache.newInstance();

    @Before
    public void clearCache() {
        observableCache.clear();
    }

    @Test
    public void flowableCompleteBeforeRotation() throws Exception {
        Disposable disposable = Flowable.just("flowable")
                .delay(3, TimeUnit.SECONDS)
                .compose(observableCache.cacheFlowable("flowable"))
                .subscribe();
        TimeUnit.SECONDS.sleep(5);
        disposable.dispose();
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void flowableCompleteAfterRotation() throws Exception {
        Flowable<String> flowable = Flowable.just("flowable")
                .delay(5, TimeUnit.SECONDS)
                .compose(observableCache.cacheFlowable("flowable"));
        Disposable disposable = flowable.subscribe();
        TimeUnit.SECONDS.sleep(1);
        disposable.dispose();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        observableCache.getFlowable("flowable").ifPresent(flowableFromCache -> compositeDisposable.add(flowableFromCache.subscribe()));
        TimeUnit.SECONDS.sleep(7);
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void singleCompleteBeforeRotation() throws Exception {
        Disposable disposable = Single.just("single")
                .delay(3, TimeUnit.SECONDS)
                .compose(observableCache.cacheSingle("single"))
                .subscribe();
        TimeUnit.SECONDS.sleep(5);
        disposable.dispose();
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void singleCompleteAfterRotation() throws Exception {
        Single<String> single = Single.just("single")
                .delay(5, TimeUnit.SECONDS)
                .compose(observableCache.cacheSingle("single"));
        Disposable disposable = single.subscribe();
        TimeUnit.SECONDS.sleep(1);
        disposable.dispose();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        observableCache.getSingle("single").ifPresent(singleFromCache -> compositeDisposable.add(singleFromCache.subscribe()));
        TimeUnit.SECONDS.sleep(7);
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void maybeCompleteBeforeRotation() throws Exception {
        Disposable disposable = Maybe.just("maybe")
                .delay(3, TimeUnit.SECONDS)
                .compose(observableCache.cacheMaybe("maybe"))
                .subscribe();
        TimeUnit.SECONDS.sleep(5);
        disposable.dispose();
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void maybeCompleteAfterRotation() throws Exception {
        Maybe<String> maybe = Maybe.just("maybe")
                .delay(5, TimeUnit.SECONDS)
                .compose(observableCache.cacheMaybe("maybe"));
        Disposable disposable = maybe.subscribe();
        TimeUnit.SECONDS.sleep(1);
        disposable.dispose();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        observableCache.getMaybe("maybe").ifPresent(maybeFromCache -> compositeDisposable.add(maybeFromCache.subscribe()));
        TimeUnit.SECONDS.sleep(7);
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void observableCompleteBeforeRotation() throws Exception {
        Disposable disposable = Observable.just("observable")
                .delay(3, TimeUnit.SECONDS)
                .compose(observableCache.cacheObservable("observable"))
                .subscribe();
        TimeUnit.SECONDS.sleep(5);
        disposable.dispose();
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void observableCompleteAfterRotation() throws Exception {
        Observable<String> observable = Observable.just("observable")
                .delay(5, TimeUnit.SECONDS)
                .compose(observableCache.cacheObservable("observable"));
        Disposable disposable = observable.subscribe();
        TimeUnit.SECONDS.sleep(1);
        disposable.dispose();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        observableCache.getObservable("observable").ifPresent(observableFromCache -> compositeDisposable.add(observableFromCache.subscribe()));
        TimeUnit.SECONDS.sleep(7);
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void completableCompleteBeforeRotation() throws Exception {
        Disposable disposable = Completable.complete()
                .delay(3, TimeUnit.SECONDS)
                .compose(observableCache.cacheCompletable("completable"))
                .subscribe();
        TimeUnit.SECONDS.sleep(5);
        disposable.dispose();
        Assert.assertTrue(observableCache.isEmpty());
    }

    @Test
    public void completableCompleteAfterRotation() throws Exception {
        Completable completable = Completable.complete()
                .delay(5, TimeUnit.SECONDS)
                .compose(observableCache.cacheCompletable("completable"));
        Disposable disposable = completable.subscribe();
        TimeUnit.SECONDS.sleep(1);
        disposable.dispose();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        observableCache.getCompletable("completable").ifPresent(completableFromCache -> compositeDisposable.add(completableFromCache.subscribe()));
        TimeUnit.SECONDS.sleep(7);
        Assert.assertTrue(observableCache.isEmpty());
    }
}