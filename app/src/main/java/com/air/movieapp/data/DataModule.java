package com.air.movieapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;


@Module
public class DataModule {

    private static final String SHARED_PREFERENCE_FILE_NAME = "movie_app_pref";

    private Context mContext;

    public DataModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    public DatabaseHelper provideRealm() {

        // Initialize Realm
        Realm.init(mContext);
        // configure Realm
      /*  RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
              *//*  .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)*//*
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);*/

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        return new DatabaseHelper(realm);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return mContext.getSharedPreferences(
                SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public PreferenceHelper providePreferenceHelper(SharedPreferences sharedPreferences) {
        return new PreferenceHelper(sharedPreferences);
    }

}
