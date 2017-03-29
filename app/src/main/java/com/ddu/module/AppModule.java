package com.ddu.module;

import android.app.Application;
import android.content.Context;

import com.ddu.app.App;


/**
 * Created by yzbzz on 16/4/15.
 */
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    Application providesApplication() {
        return mApp;
    }

    Context providesContext() {
        return mApp;
    }
}
