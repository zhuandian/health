package com.zhuandian.health;

import android.app.Application;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-10.
 */
public class App extends Application {

    private static App instance;
    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
