package com.goshopping.usx.goshopping;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Si on 2016/10/14.
 */

public class GoShoppingApp extends Application {

    public static GoShoppingApp mInstance;


    @Override
    public void onCreate()
    {

        super.onCreate();

        mInstance = this;

        //在使用BaiduSDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
    }

    public static GoShoppingApp getInstance()
    {

        return mInstance;
    }
}
