package com.github.aleksandermielczarek.observablecacheexample.component;


import com.github.aleksandermielczarek.napkin.module.NapkinActivityModule;
import com.github.aleksandermielczarek.napkin.scope.ActivityScope;
import com.github.aleksandermielczarek.observablecacheexample.ui.Observable1Activity;
import com.github.aleksandermielczarek.observablecacheexample.ui.Observable2Activity;

import dagger.Subcomponent;

/**
 * Created by Aleksander Mielczarek on 12.11.2016.
 */
@ActivityScope
@Subcomponent(modules = NapkinActivityModule.class)
public interface ActivityComponent {

    void inject(Observable1Activity observable1Activity);

    void inject(Observable2Activity observable2Activity);
}
