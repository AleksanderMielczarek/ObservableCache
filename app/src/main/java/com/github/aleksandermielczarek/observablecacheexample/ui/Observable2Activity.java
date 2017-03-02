package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.napkin.Napkin;
import com.github.aleksandermielczarek.napkin.module.NapkinActivityModule;
import com.github.aleksandermielczarek.observablecacheexample.R;
import com.github.aleksandermielczarek.observablecacheexample.component.AppComponent;
import com.github.aleksandermielczarek.observablecacheexample.databinding.ActivityObservable2Binding;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;

import javax.inject.Inject;

/**
 * Created by Aleksander Mielczarek on 10.02.2017.
 */
@EActivity
public class Observable2Activity extends NavigationActivity {

    @InstanceState
    protected Observable2ViewModel.State state;

    @Inject
    protected Observable2ViewModel observable2ViewModel;

    @Override
    protected void inject() {
        Napkin.<AppComponent>provideAppComponent(this)
                .with(new NapkinActivityModule(this))
                .inject(this);
    }

    @Override
    protected int contentLayout() {
        return R.layout.activity_observable2;
    }

    @Override
    protected void onCreateContent(@Nullable Bundle savedInstanceState) {
        observable2ViewModel.restoreState(state);
        ActivityObservable2Binding binding = getContentBinding();
        setSupportActionBar(binding.toolbar);
        binding.setViewModel(observable2ViewModel);
    }

    @Override
    protected int selectedBottomMenuItem() {
        return BOTTOM_MENU_ITEM_RXJAVA_2;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = observable2ViewModel.saveState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable2ViewModel.restoreObservables();
    }

    @Override
    protected void onStop() {
        super.onStop();
        observable2ViewModel.dispose();
    }
}