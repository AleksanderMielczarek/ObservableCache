package com.github.aleksandermielczarek.observablecacheexample.component;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecacheexample.module.AppModule;
import com.github.aleksandermielczarek.observablecacheexample.ui.MainActivity;

import dagger.Component;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@Component(modules = AppModule.class)
@AppScope
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
