package com.polar.android_seed.app;

import android.app.Application;

import com.polar.android_seed.helpers.DBHelper;
import com.polar.android_seed.helpers.LogoutHelper;
import com.polar.android_seed.sync.RequestManager;

public class myApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(getApplicationContext());
        DBHelper.init(getApplicationContext());
        LogoutHelper.init(getApplicationContext());
    }
}

