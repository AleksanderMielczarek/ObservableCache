package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.content.Context;
import android.content.Intent;

import com.github.aleksandermielczarek.napkin.qualifier.ActivityContext;

import org.androidannotations.api.builder.ActivityIntentBuilder;

import javax.inject.Inject;

/**
 * Created by Aleksander Mielczarek on 10.02.2017.
 */

public class NavigationViewModel {

    private final Context context;

    @Inject
    public NavigationViewModel(@ActivityContext Context context) {
        this.context = context;
    }

    public void openRxJava1() {
        openNavigationItem(Observable1Activity_.intent(context));
    }

    public void openRxJava2() {
        openNavigationItem(Observable2Activity_.intent(context));
    }

    private void openNavigationItem(ActivityIntentBuilder<?> activityIntentBuilder) {
        activityIntentBuilder
                .flags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .start();
    }
}
