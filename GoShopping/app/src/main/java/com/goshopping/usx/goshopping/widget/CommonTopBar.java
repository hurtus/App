package com.goshopping.usx.goshopping.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * Created by Si on 2016/10/15.
 */

public class CommonTopBar extends Toolbar {


    //自定义toolbar，根据项目到时候的需求在更改
    public CommonTopBar(Context context) {
        super(context);
    }

    public CommonTopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonTopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
