package com.github.aleksandermielczarek.observablecacheexample;

import android.support.test.runner.AndroidJUnit4;

import com.github.aleksandermielczarek.observablecache2.ObservableCache;
import com.github.aleksandermielczarek.observablecache2.LruObservableCache;
import com.github.aleksandermielczarek.observablecache2.service.ObservableCacheService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Created by Aleksander Mielczarek on 02.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ObservableCacheService2Test {

    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final int DELAY = 3;
    public static final int TIMEOUT = 10;
    public static final int ROTATE_TIMEOUT = 1;
    public static final String RESULT = "result";
    public static final String ERROR = "error";

    private final ObservableCache observableCache = LruObservableCache.newInstance();
    private final ObservableCacheService observableCacheService = new ObservableCacheService(observableCache);
    private final Cached2Service cachedService = observableCacheService.createObservableCacheService(Cached2Service.class);
    private final AtomicReference<String> result = new AtomicReference<>();
    private final AtomicBoolean completableResult = new AtomicBoolean();
    private final AtomicReference<String> error = new AtomicReference<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public Flowable<String> flowable() {
        return Flowable.fromCallable(() -> RESULT)
                .delay(DELAY, TIME_UNIT);
    }

    public Observable<String> observable() {
        return Observable.fromCallable(() -> RESULT)
                .delay(DELAY, TIME_UNIT);
    }

    public Maybe<String> maybe() {
        return Maybe.fromCallable(() -> RESULT)
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

    public Flowable<String> flowableError() {
        return flowable().map(observable -> {
            throw new IllegalArgumentException(ERROR);
        });
    }

    public Observable<String> observableError() {
        return observable().map(observable -> {
            throw new IllegalArgumentException(ERROR);
        });
    }

    public Maybe<String> maybeError() {
        return maybe().map(single -> {
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
        disposables.clear();
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

    public Consumer<String> onNext() {
        return result::set;
    }

    public Action onNextCompletable() {
        return () -> completableResult.set(true);
    }

    public Consumer<Throwable> onError() {
        return throwable -> error.set(throwable.getMessage());
    }

    @Before
    public void clear() {
        observableCache.clear();
        result.set("");
        error.set("");
        completableResult.set(false);
        disposables.clear();
    }

    @Test
    public void flowableCompleteBeforeRotation() throws Exception {
        disposables.add(flowable()
                .compose(cachedService.flowable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void flowableCompleteAfterRotation() throws Exception {
        disposables.add(flowable()
                .compose(cachedService.flowable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedFlowable().ifPresent(flowableFromCache -> disposables.add(flowableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void flowableErrorCompleteBeforeRotation() throws Exception {
        disposables.add(flowableError()
                .compose(cachedService.flowable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void flowableErrorCompleteAfterRotation() throws Exception {
        disposables.add(flowableError()
                .compose(cachedService.flowable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedFlowable().ifPresent(flowableFromCache -> disposables.add(flowableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void observableCompleteBeforeRotation() throws Exception {
        disposables.add(observable()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void observableCompleteAfterRotation() throws Exception {
        disposables.add(observable()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedObservable().ifPresent(observableFromCache -> disposables.add(observableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void observableErrorCompleteBeforeRotation() throws Exception {
        disposables.add(observableError()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void observableErrorCompleteAfterRotation() throws Exception {
        disposables.add(observableError()
                .compose(cachedService.observable())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedObservable().ifPresent(observableFromCache -> disposables.add(observableFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void maybeCompleteBeforeRotation() throws Exception {
        disposables.add(maybe()
                .compose(cachedService.maybe())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void maybeCompleteAfterRotation() throws Exception {
        disposables.add(maybe()
                .compose(cachedService.maybe())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedMaybe().ifPresent(maybeFromCache -> disposables.add(maybeFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void maybeErrorCompleteBeforeRotation() throws Exception {
        disposables.add(maybeError()
                .compose(cachedService.maybe())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void maybeErrorCompleteAfterRotation() throws Exception {
        disposables.add(maybeError()
                .compose(cachedService.maybe())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedMaybe().ifPresent(maybeFromCache -> disposables.add(maybeFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void singleCompleteBeforeRotation() throws Exception {
        disposables.add(single()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertResult();
    }

    @Test
    public void singleCompleteAfterRotation() throws Exception {
        disposables.add(single()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedSingle().ifPresent(singleFromCache -> disposables.add(singleFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertResult();
    }

    @Test
    public void singleErrorCompleteBeforeRotation() throws Exception {
        disposables.add(singleError()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        waitForRx();
        assertError();
    }

    @Test
    public void singleErrorCompleteAfterRotation() throws Exception {
        disposables.add(singleError()
                .compose(cachedService.single())
                .subscribe(onNext(), onError()));
        rotate();
        cachedService.cachedSingle().ifPresent(singleFromCache -> disposables.add(singleFromCache.subscribe(onNext(), onError())));
        waitForRx();
        assertError();
    }

    @Test
    public void completableCompleteBeforeRotation() throws Exception {
        disposables.add(completable()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        waitForRx();
        assertResultCompletable();
    }

    @Test
    public void completableCompleteAfterRotation() throws Exception {
        disposables.add(completable()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        rotate();
        cachedService.cachedCompletable().ifPresent(completableFromCache -> disposables.add(completableFromCache.subscribe(onNextCompletable(), onError())));
        waitForRx();
        assertResultCompletable();
    }

    @Test
    public void completableErrorCompleteBeforeRotation() throws Exception {
        disposables.add(completableError()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        waitForRx();
        assertErrorCompletable();
    }

    @Test
    public void completableErrorCompleteAfterRotation() throws Exception {
        disposables.add(completableError()
                .compose(cachedService.completable())
                .subscribe(onNextCompletable(), onError()));
        rotate();
        cachedService.cachedCompletable().ifPresent(completableFromCache -> disposables.add(completableFromCache.subscribe(onNextCompletable(), onError())));
        waitForRx();
        assertErrorCompletable();
    }
}