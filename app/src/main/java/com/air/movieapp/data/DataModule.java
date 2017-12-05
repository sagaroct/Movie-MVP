package com.air.movieapp.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;


@Module
public class DataModule {

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


}
