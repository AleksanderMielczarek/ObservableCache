package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.aleksandermielczarek.napkin.Napkin;
import com.github.aleksandermielczarek.napkin.module.NapkinActivityModule;
import com.github.aleksandermielczarek.observablecacheexample.R;
import com.github.aleksandermielczarek.observablecacheexample.component.AppComponent;
import com.github.aleksandermielczarek.observablecacheexample.databinding.ActivityObservable1Binding;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;

import javax.inject.Inject;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@EActivity
public class Observable1Activity extends NavigationActivity {

    @InstanceState
    protected Observable1ViewModel.State state;

    @Inject
    protected Observable1ViewModel observable1ViewModel;

    @Override
    protected void inject() {
        Napkin.<AppComponent>provideAppComponent(this)
                .with(new NapkinActivityModule(this))
                .inject(this);
    }

    @Override
    protected int contentLayout() {
        return R.layout.activity_observable1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LIFE", "onCreate");
    }

    @Override
    protected void onCreateContent(@Nullable Bundle savedInstanceState) {
        Log.d("LIFE", "onCreateContent");
        observable1ViewModel.restoreState(state);
        ActivityObservable1Binding binding = getContentBinding();
        setSupportActionBar(binding.toolbar);
        binding.setViewModel(observable1ViewModel);
    }

    @Override
    protected int selectedBottomMenuItem() {
        return BOTTOM_MENU_ITEM_RXJAVA_1;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = observable1ViewModel.saveState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LIFE", "onStart");
        observable1ViewModel.restoreObservables();
    }

    @Override
    protected void onStop() {
        super.onStop();
        observable1ViewModel.unsubscribe();
    }
}
