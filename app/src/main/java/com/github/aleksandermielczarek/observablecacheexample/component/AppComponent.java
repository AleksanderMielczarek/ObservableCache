package com.github.aleksandermielczarek.observablecacheexample.component;

import com.github.aleksandermielczarek.napkin.module.ActivityModule;
import com.github.aleksandermielczarek.napkin.module.AppModule;
import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecacheexample.module.MainModule;

import dagger.Component;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@Component(modules = {AppModule.class, MainModule.class})
@AppScope
public interface AppComponent {

    ActivityComponent with(ActivityModule activityModule);
}
