package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.aleksandermielczarek.napkin.Napkin;
import com.github.aleksandermielczarek.observablecacheexample.R;
import com.github.aleksandermielczarek.observablecacheexample.component.AppComponent;
import com.github.aleksandermielczarek.observablecacheexample.databinding.ActivityMainBinding;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;

import javax.inject.Inject;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@EActivity
public class MainActivity extends AppCompatActivity {

    @InstanceState
    protected MainViewModel.State state;

    @Inject
    protected MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Napkin.provideComponent(this, AppComponent.class).inject(this);
        mainViewModel.restoreState(state);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = mainViewModel.saveState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.restoreObservables();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainViewModel.unsubscribe();
    }
}
