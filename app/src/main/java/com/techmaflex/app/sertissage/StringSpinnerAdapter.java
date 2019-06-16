package com.techmaflex.app.sertissage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.techmaflex.app.util.LayoutUtil;

import java.util.List;

public class StringSpinnerAdapter extends ArrayAdapter<String> {

    private final Callback mCallback;
    private final String mSpinnerId;
    private final Context mContext;
    private List<String> mDatas;
    private float largestItemViewPxSize;

    interface Callback {
        void onLargestItemViewPxChanged(float largestItemViewDpSize);
    }

    public StringSpinnerAdapter(@NonNull Context context, @NonNull List<String> datas, Callback callback, String spinnerId) {
        super(context, android.R.layout.simple_spinner_item, datas);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDatas = datas;
        mCallback = callback;
        mSpinnerId = spinnerId;
        mContext = context;
    }

    public void swapData(List<String> datas) {
        if (datas == null) {
            return;
        }
        clear();
        addAll(datas); //Le addAll fait un notifyDataSetChanged pas besoin de le faire
        mDatas = datas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        view.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                        "spinner : " + mSpinnerId +
                        " largestItemViewPxSize : " + largestItemViewPxSize +
                        " view.getMeasuredWidth : " + view.getMeasuredWidth());
                boolean hasWidthChanged = view.getMeasuredWidth() > largestItemViewPxSize;
                largestItemViewPxSize = hasWidthChanged ? view.getMeasuredWidth() : largestItemViewPxSize;

                //view.setLayoutParams(new ViewGroup.LayoutParams((int) largestItemViewPxSize, view.getMeasuredHeight()));
                view.getLayoutParams().width = (int) new LayoutUtil().toPx(mContext, largestItemViewPxSize);
                Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                        "spinner : " + mSpinnerId + "set done "+
                        " largestItemViewPxSize : " + largestItemViewPxSize +
                        " view.getMeasuredWidth : " + view.getMeasuredWidth());

                if (hasWidthChanged) {
                    Log.e("Tech", Thread.currentThread().getStackTrace()[2] + "hasWidthChanged");
                    mCallback.onLargestItemViewPxChanged(largestItemViewPxSize);
                }
            }
        });
        return view;
    }

    public void setAllItemsWidth(int width) {
        Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                "spinner : " + mSpinnerId +
                " largestItemViewPxSize : " + largestItemViewPxSize +
                " view.getMeasuredWidth : " + width);
        boolean isDemandedWidthLarger = width > largestItemViewPxSize;
        largestItemViewPxSize = isDemandedWidthLarger ? width : largestItemViewPxSize;
        if (isDemandedWidthLarger) {
            swapData(mDatas);
        }
    }
}
