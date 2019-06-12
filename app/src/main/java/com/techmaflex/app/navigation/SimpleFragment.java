package com.techmaflex.app.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SimpleFragment extends Fragment {

    private FrameLayout mView;
    private Callback mCallback;

    interface Callback {
        void onLoadFinished();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(params);
        return mView;
    }

    public void addCallback(Callback callback) {
        mCallback = callback;
    }

    public void setView(View view) {
        ViewGroup parent = ((ViewGroup) view.getParent());
        if (parent != null) {
            parent.removeAllViews();
        }
        mView.addView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCallback != null) {
            mCallback.onLoadFinished();
        }
    }
}
