package com.github.aleksandermielczarek.observablecacheexample;

import android.app.Application;

import com.github.aleksandermielczarek.napkin.ComponentProvider;
import com.github.aleksandermielczarek.napkin.module.AppModule;
import com.github.aleksandermielczarek.observablecacheexample.component.AppComponent;
import com.github.aleksandermielczarek.observablecacheexample.component.DaggerAppComponent;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public class ObservableCacheApplication extends Application implements ComponentProvider<AppComponent> {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public AppComponent provideComponent() {
        return appComponent;
    }
}
