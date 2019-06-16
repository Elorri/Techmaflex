package com.techmaflex.app.sertissage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class StringSpinnerAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private List<String> mDatas;
    private float largestItemViewPxSize;


    public StringSpinnerAdapter(@NonNull Context context, @NonNull List<String> datas) {
        super(context, android.R.layout.simple_spinner_item, datas);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDatas = datas;
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

}
