package com.debadri.doin;

import android.app.Application;

import io.realm.Realm;

public class SubApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
