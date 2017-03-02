package com.github.aleksandermielczarek.observablecacheexample.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.aleksandermielczarek.observablecacheexample.R;
import com.github.aleksandermielczarek.observablecacheexample.databinding.ActivityNavigationBinding;

import javax.inject.Inject;

/**
 * Created by Aleksander Mielczarek on 10.02.2017.
 */

public abstract class NavigationActivity extends AppCompatActivity {

    @IntDef({BOTTOM_MENU_ITEM_RXJAVA_1, BOTTOM_MENU_ITEM_RXJAVA_2})
    @interface BottomMenuItem {
    }

    public static final int BOTTOM_MENU_ITEM_RXJAVA_1 = 0;
    public static final int BOTTOM_MENU_ITEM_RXJAVA_2 = 1;

    @Inject
    protected NavigationViewModel navigationViewModel;

    protected ActivityNavigationBinding navigationBinding;
    private ViewDataBinding contentBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        navigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        setupNavigation();
        setupContent(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    private void setupContent(@Nullable Bundle savedInstanceState) {
        navigationBinding.content.setOnInflateListener((stub, inflated) -> {
            contentBinding = navigationBinding.content.getBinding();
            onCreateContent(savedInstanceState);
        });
        navigationBinding.content.getViewStub().setLayoutResource(contentLayout());
        navigationBinding.content.getViewStub().inflate();
    }

    private void setupNavigation() {
        int selectedItem = selectedBottomMenuItem();
        navigationBinding.bottomNavigation.getMenu().getItem(selectedItem).setChecked(true);
        navigationBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_rxjava_1:
                    navigationViewModel.openRxJava1();
                    break;
                case R.id.action_rxjava_2:
                    navigationViewModel.openRxJava2();
                    break;
            }
            return false;
        });
    }

    @SuppressWarnings("unchecked")
    protected <T extends ViewDataBinding> T getContentBinding() {
        return (T) contentBinding;
    }

    protected abstract void inject();

    @LayoutRes
    protected abstract int contentLayout();

    protected abstract void onCreateContent(@Nullable Bundle savedInstanceState);

    @BottomMenuItem
    protected abstract int selectedBottomMenuItem();
}
