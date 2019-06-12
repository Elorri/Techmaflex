package com.techmaflex.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class NotFoundView extends LinearLayout {

    public NotFoundView(@NonNull Context context){
        this(context, null);
    }

    public NotFoundView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.page_not_found, this);
    }

}
