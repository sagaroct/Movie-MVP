package com.air.movieapp.application;

import android.app.Application;
import android.content.Context;

import com.air.movieapp.injection.module.AppModule;
import com.air.movieapp.injection.module.DataModule;
import com.air.movieapp.injection.component.AppComponent;
import com.air.movieapp.injection.component.DaggerAppComponent;
import com.air.movieapp.injection.module.NetworkModule;
import com.air.movieapp.injection.module.RxBusModule;

/**
 * Created by sagar on 4/8/17.
 */

public class MovieApplication extends Application {

    public AppComponent mAppComponent;

    public static MovieApplication get(Context context) {
        return (MovieApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .appModule(new AppModule(this))
                .rxBusModule(new RxBusModule())
                .dataModule(new DataModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
