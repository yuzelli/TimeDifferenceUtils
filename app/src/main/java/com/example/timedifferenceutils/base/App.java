package com.example.timedifferenceutils.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by DeepRolling on 2017/10/24.
 */

public class App extends Application {
    public static Context applicationContext;
    public static AppCompatActivity activity;

    @Override
    public void onCreate() {
        applicationContext = this;

        super.onCreate();
    }

    public static Context getAppContext() {
        return applicationContext;
    }

    public static AppCompatActivity getAcitivityContext() {
        return activity;
    }

}
