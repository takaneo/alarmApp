package com.example.takahiro.alarmapp;

import android.app.Application;

/**
 * Created by Takahiro on 2016/01/21.
 */
public class StethoTest extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

}
