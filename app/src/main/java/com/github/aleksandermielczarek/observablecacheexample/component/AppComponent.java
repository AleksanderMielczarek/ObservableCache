package com.github.aleksandermielczarek.observablecacheexample.component;

import com.github.aleksandermielczarek.napkin.module.NapkinActivityModule;
import com.github.aleksandermielczarek.napkin.module.NapkinAppModule;
import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.observablecacheexample.module.Observable1Module;
import com.github.aleksandermielczarek.observablecacheexample.module.Observable2Module;

import dagger.Component;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@Component(modules = {NapkinAppModule.class, Observable1Module.class, Observable2Module.class})
@AppScope
public interface AppComponent {

    ActivityComponent with(NapkinActivityModule activityModule);
}
