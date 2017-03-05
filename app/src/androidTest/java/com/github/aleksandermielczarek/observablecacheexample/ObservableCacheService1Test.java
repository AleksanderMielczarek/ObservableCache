package com.github.aleksandermielczarek.observablecacheexample;

import android.support.test.runner.AndroidJUnit4;

import com.github.aleksandermielczarek.observablecache.ObservableCache;
import com.github.aleksandermielczarek.observablecache.LruObservableCache;
import com.github.aleksandermielczarek.observablecache.service.ObservableCacheService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Aleksander Mielczarek on 02.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ObservableCacheService1Test {

    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final int DELAY = 3;
    public static final int TIMEOUT = 10;
    public static final int ROTATE_TIMEOUT = 1;
    public static final String RESULT = "result";
    public static final String ERROR = "error";

    private final ObservableCache observableCache = LruObservableCache.newInstance();
    private final ObservableCacheService observableCacheService = new ObservableCacheService(observableCache);
    private final Cached1Service cachedService = observableCacheService.createObservableCacheService(Cached1Service.class);
    private final AtomicReference<String> result = new AtomicReference<>();
    private final AtomicBoolean completableResult = new AtomicBoolean();
    private final AtomicReference<String> error = new AtomicReference<>();
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public Observable<String> observable() {
        return Observable.fromCallable(() -> RESULT)
                .delay(DELAY, TIME_UNIT);
    }

    public Single<String> single() {
        return Single.fromCallable(() -> RESULT)
                .delay(DELAY, TIME_UNIT);
    }

    public Completable completable() {
        return Completable.fromCallable(() -> null)
                .delay(DELAY, TIME_UNIT);
    }

    public Observable<String> observableError() {
        return observable().map(observable -> {
            throw new IllegalArgumentException(ERROR);
        });
    }

    public Single<String> singleError() {
        return single().map(single -> {
            throw new IllegalArgumentException(ERROR);
        });
    }

    public Completable completableError() {
        return completable().andThen(Completable.fromCallable(() -> {
            throw new IllegalArgumentException(ERROR);
        }));
    }

    public void waitForRx() throws InterruptedException {
        TIME_UNIT.sleep(TIMEOUT);
    }

    public void rotate() throws InterruptedException {
        TIME_UNIT.sleep(ROTATE_TIMEOUT);
        subscriptions.clear();
        result.set("");
        error.set("");
        completableResult.set(false);
    }

    public void assertError() {
        Assert.assertTrue(observableCache.isEmpty());
        Assert.assertEquals(ERROR, error.get());
        Assert.assertEquals("", result.get());
    }

    public void assertErrorCompletable() {
        Assert.assertTrue(observableCache.isEmpty());
        Assert.assertEquals(ERROR, error.get());
        Assert.assertFalse(completableResult.get());
    }

    public void assertResult() {
        Assert.assertTrue(observableCache.isEmpty());
        Assert.assertEquals(RESULT, result.get());
        Assert.assertEquals("", error.get());
    }

    public void assertResultCompletable() {
        Assert.assertTrue(observableCache.isEmpty());
        Assert.assertTrue(completableResult.get());
        Assert.assertEquals("", error.get());
    }

    public Action1<String> onNext() {
        return result::set;
    }

    public Action0 onNextCompletable() {
        return () -> completableResult.set(true);
    }

    public Action1<Throwable> onError() {
        return throwable -> error.set(throwable.getMessage());
    }

    @Before
    public void clear() {
        observableCache.clear();
        result.set("");
        error.set("");
        completableResult.set(false);
        subscriptions.clear();
    }

    @Test
    public void observableCompleteBeforeRotation() throws Exception {
        subscriptions.add(observable()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void observableCompleteAfterRotation() throws Exception {
        subscriptions.add(observable()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedObservable().ifPresent(observableFromCache -> subscriptions.add(observableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void observableErrorCompleteBeforeRotation() throws Exception {
        subscriptions.add(observableError()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void observableErrorCompleteAfterRotation() throws Exception {
        subscriptions.add(observableError()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedObservable().ifPresent(observableFromCache -> subscriptions.add(observableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void singleCompleteBeforeRotation() throws Exception {
        subscriptions.add(single()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void singleCompleteAfterRotation() throws Exception {
        subscriptions.add(single()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedSingle().ifPresent(singleFromCache -> subscriptions.add(singleFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void singleErrorCompleteBeforeRotation() throws Exception {
        subscriptions.add(singleError()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void singleErrorCompleteAfterRotation() throws Exception {
        subscriptions.add(singleError()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedSingle().ifPresent(singleFromCache -> subscriptions.add(singleFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void completableCompleteBeforeRotation() throws Exception {
        subscriptions.add(completable()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        waitForRx();
        assertResultCompletable();
    }

    @Test
    public void completableCompleteAfterRotation() throws Exception {
        subscriptions.add(completable()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        rotate();
        cachedService.cachedCompletable().ifPresent(completableFromCache -> subscriptions.add(completableFromCache.subscribe(onNextCompletable(), onError())));
        waitForRx();
        assertResultCompletable();
    }

    @Test
    public void completableErrorCompleteBeforeRotation() throws Exception {
        subscriptions.add(completableError()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        waitForRx();
        assertErrorCompletable();
    }

    @Test
    public void completableErrorCompleteAfterRotation() throws Exception {
        subscriptions.add(completableError()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        rotate();
        cachedService.cachedCompletable().ifPresent(completableFromCache -> subscriptions.add(completableFromCache.subscribe(onNextCompletable(), onError())));
        waitForRx();
        assertErrorCompletable();
    }
}