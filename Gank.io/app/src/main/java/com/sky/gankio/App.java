package com.sky.gankio;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sky.gankio.http.CrashHandler;

/**
 * Created by tonycheng on 2016/11/24.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        Fresco.initialize(this);
        CrashHandler.getInstance().init(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
