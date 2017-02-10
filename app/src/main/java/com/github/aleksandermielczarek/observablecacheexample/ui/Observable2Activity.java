package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
public class Observable2Activity extends AppCompatActivity {

    @InstanceState
    protected Observable2ViewModel.State state;

    @Inject
    protected Observable2ViewModel observable2ViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Napkin.<AppComponent>provideAppComponent(this)
                .with(new NapkinActivityModule(this))
                .inject(this);
        observable2ViewModel.restoreState(state);
        ActivityObservable2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_observable2);
        binding.setViewModel(observable2ViewModel);
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