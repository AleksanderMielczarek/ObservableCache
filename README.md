[![](https://jitpack.io/v/AleksanderMielczarek/ObservableCache.svg)](https://jitpack.io/#AleksanderMielczarek/ObservableCache)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ObservableCache-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4628)

# ObservableCache

[RxJava](https://github.com/ReactiveX/RxJava) has become a standard in Android development. It's great 
until you have to deal with Android lifecycle. Normally you unsubscribe when view is destroyed and 
create new Observable after view is recreated. It's ok in most cases but sometimes there are actions,
which cannot be done more than once i.e. HTTP Request which must be done once and Resposne must be received.
In that cases Observables must be kept in place which lifecyle is different than destroyed view. 
This is where ObservableCache can be used. Library allows to cache Observable in global singleton map and
retrieve same Observable after view is recreated. Internally library uses 
[cache()](http://reactivex.io/RxJava/javadoc/rx/Observable.html#cache()) for caching. Observables are automatically 
removed after onComplete.

## RxJava 1.x

### Observable Cache

#### Usage

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency

```groovy
dependencies {
    compile 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-1:1.1.1'
}
```

#### Example

```java
public class MainActivity extends AppCompatActivity {

    public static final String OBSERVABLE_CACHE_KEY_REQUEST = "observableRequest";

    private ObservableCache observableCache;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observableCache = LruObservableCache.getDefault();//get default singleton instance
        subscriptions = new CompositeSubscription();

    }

    public void performObservableAction() {
        Observable<String> observable = Observable.just("Test Action");
        observableAction(observable
                .compose(observableCache.cacheObservable(OBSERVABLE_CACHE_KEY_REQUEST)));//this line is responsible for caching observable
    }

    private void observableAction(Observable<String> testObservable) {
        subscriptions.add(testObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {/*do sth with result*/});
    }

    @Override
    protected void onStart() {
        super.onStart();
        observableCache.<String>getObservable(OBSERVABLE_CACHE_KEY_REQUEST).ifPresent(this::observableAction);//retrieve observable from cache and perform action if observable exists
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.clear();
    }
}
```

#### More information 

- caching Observable<T>:
```java
CacheableObservable<T> cachable = observableCache.cacheObservable(KEY);
```
- caching Single<T>: 
```java
CacheableSingle<T> cachable = observableCache.cacheSingle(KEY);
```
- caching Completable<T>: 
```java
CacheableCompletable<T> cachable = observableCache.cacheCompletable(KEY);
```
- retrieve Observable<T>: 
```java
ObservableFromCache<T> fromCache = observableCache.<T>getObservable(KEY);
```
- retrieve Single<T>: 
```java
SingleFromCache<T> fromCache = observableCache.<T>getSingle(KEY);
```
- retrieve Completable<T>: 
```java
CompletableFromCache<T> fromCache = observableCache.<T>getCompletable(KEY);
```
- remove cached value: 
```java
boolean removed = observableCache.remove(KEY);
```
- get new instance of cache: 
```java
ObservableCache observableCache = LruObservableCache.newInstance();
```
- cache based on Map:
```java
ObservableCache observableCache = MapObservableCache.newInstance();
```

### Observable Cache Service

Using ObservableCache requires from developer writing unique keys for cached Observables. This can be 
error prone and that's why additional layer can be used. Instead of directly using ObservableCache 
and manually manipulating keys, Observable Cache Service generate classes from declared interfaces which
internally assures that all keys are unique.  

#### Usage

Add it in your root build.gradle at the end of repositories:

Add to the dependencies

```groovy
dependencies {
    compile 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-1-service:1.1.1'
    apt 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-service-1-processor:1.1.1'
}
```

#### Example

Previous example can be replaced with following implementation.

```java
@ObservableCacheService
public interface CachedService {

    CacheableObservable<String> testObservable();

    ObservableFromCache<String> cachedTestObservable();

    boolean removeTestObservable();

}
```

```java
public class MainActivity extends AppCompatActivity {

    private CachedService cachedService;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObservableCache observableCache = LruObservableCache.getDefault();
        ObservableCacheService observableCacheService = new ObservableCacheService(observableCache);
        cachedService = observableCacheService.createObservableCacheService(CachedService.class);
        subscriptions = new CompositeSubscription();
    }

    public void performObservableAction() {
        Observable<String> observable = Observable.just("Test Action");
        observableAction(observable
                .compose(cachedService.testObservable()));//this line is responsible for caching observable
    }

    private void observableAction(Observable<String> testObservable) {
        subscriptions.add(testObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {/*do sth with result*/});
    }

    @Override
    protected void onStart() {
        super.onStart();
        cachedService.cachedTestObservable().ifPresent(this::observableAction);//retrieve observable from cache and perform action if observable exists
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.clear();
    }
}
```

### More information

Keys are generated based on method names:

- key value is based on method name that takes 0 arguments and returns CacheableObservable<T>, 
CacheableSingle<T> or CacheableCompletable
- method that retrieves value from cache takes 0 arguments and returns ObservableFromCache<T>, 
SingleFromCache<T> or CompletableFromCache<T>. Name of this method must be the same as method for 
caching values + word 'cached': 
    - cache: 'testObservable()', retrieve: '**cached**TestObservable()'
    - cache: 'testObservable()', retrieve: 'test**Cached**Observable()'
    - cache: 'testObservable()', retrieve: 'testObservable**Cached**()'
- method that removes value from cache takes 0 arguments and returns boolean.Name of this method 
must be the same as method for caching values + word 'remove': 
    - cache: 'testObservable()', remove: '**remove**TestObservable()'
    - cache: 'testObservable()', remove: 'test**Remove**Observable()'
    - cache: 'testObservable()', remove: 'testObservable**Remove**()'

### ProGuard

ProGuard is shipped with library.

## RxJava 2.x

RxJava 2 usage is very similar to RxJava 1.

New types:

- caching Flowable<T>:
```java
CacheableFlowable<T> cachable = observableCache.cacheFlowable(KEY);
```
- caching Maybe<T>:
```java
CacheableMaybe<T> cachable = observableCache.cacheMaybe(KEY);
```
- retrieve Flowable<T>:
```java
FlowableFromCache<T> fromCache = observableCache.<T>getFlowable(KEY);
```
- retrieve Maybe<T>:
```java
MaybeFromCache<T> fromCache = observableCache.<T>getMaybe(KEY);
```

### Observable Cache

#### Usage

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency

```groovy
dependencies {
    compile 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-2:1.1.1'
}
```

### Observable Cache Service

#### Usage

Add it in your root build.gradle at the end of repositories:

Add to the dependencies

```groovy
dependencies {
    compile 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-2-service:1.1.1'
    apt 'com.github.AleksanderMielczarek.ObservableCache:observable-cache-service-2-processor:1.1.1'
}
```

### ProGuard

ProGuard is shipped with library.

## Changelog

### 1.1.1 (2017-03-03)

- fix issue that does not remove Single and Maybe from cache
- add ProGuard rules to library

### 1.1.0 (2017-02-10)

- add RxJava 2.x support
- rename RxJava 1.x modules

### 1.1.1 (2016-11-06)

- add generator for caching interface

## License

    Copyright 2016 Aleksander Mielczarek

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
