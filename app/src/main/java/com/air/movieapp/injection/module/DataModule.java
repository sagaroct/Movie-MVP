package com.air.movieapp.injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.air.movieapp.data.local.DatabaseHelper;
import com.air.movieapp.data.local.PreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;


@Module
public class DataModule {

    private static final String SHARED_PREFERENCE_FILE_NAME = "movie_app_pref";

//    private Context mContext;

    /*public DataModule(Context mContext) {
        this.mContext = mContext;
    }*/

    @Provides
    @Singleton
    public DatabaseHelper provideRealm(Context context) {
        // Initialize Realm
        Realm.init(context);
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        return new DatabaseHelper(realm);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(
                SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public PreferenceHelper providePreferenceHelper(SharedPreferences sharedPreferences) {
        return new PreferenceHelper(sharedPreferences);
    }

}
