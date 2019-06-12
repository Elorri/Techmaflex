package com.techmaflex.app.navigation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class DisplayViewWithFragment implements DisplayView, SimpleFragment.Callback {

    private final AppCompatActivity mActivity;
    private SimpleFragment mSimpleFragment;
    private View mView;

    public DisplayViewWithFragment(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void show(ViewGroup container, View view) {
        if (container.getId() == View.NO_ID) {

        }
        mView = view;
        mSimpleFragment = new SimpleFragment();
        mSimpleFragment.addCallback(this);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(container.getId(), mSimpleFragment)
                .addToBackStack("simple fragment")
                .commit();

    }


    @Override
    public void onLoadFinished() {
        mSimpleFragment.setView(mView);
    }
}
