package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
public class Observable1Activity extends AppCompatActivity {

    @InstanceState
    protected Observable1ViewModel.State state;

    @Inject
    protected Observable1ViewModel observable1ViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Napkin.<AppComponent>provideAppComponent(this)
                .with(new NapkinActivityModule(this))
                .inject(this);
        observable1ViewModel.restoreState(state);
        ActivityObservable1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_observable1);
        binding.setViewModel(observable1ViewModel);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = observable1ViewModel.saveState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable1ViewModel.restoreObservables();
    }

    @Override
    protected void onStop() {
        super.onStop();
        observable1ViewModel.unsubscribe();
    }
}
