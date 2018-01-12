package com.air.movieapp.network;

import android.content.Context;

import com.air.movieapp.BuildConfig;
import com.air.movieapp.common.NetworkUtils;
import com.air.movieapp.common.RestConstants;
import com.air.movieapp.data.DatabaseHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    private Context mContext;

    public NetworkModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    @Named("RxParsing")
    Retrofit provideCall() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        OkHttpClient okHttpClient = builder.build();
        return new Retrofit.Builder()
                .baseUrl(RestConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("SimpleParsing")
    Retrofit provideRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        OkHttpClient okHttpClient = builder.build();
        return new Retrofit.Builder()
                .baseUrl(RestConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

    @Provides
    @Singleton
    @Named("SimpleInterface")
    public MovieApiService providesApiSimpleInterface(
            @Named("SimpleParsing") Retrofit retrofit) {
        return retrofit.create(MovieApiService.class);
    }

    @Provides
    @Singleton
    @Named("RxInterface")
    public MovieApiService providesApiRxInterface(
            @Named("RxParsing") Retrofit retrofit) {
        return retrofit.create(MovieApiService.class);
    }

    @Provides
    @Singleton
    public NetworkUtils provideNetworkUtils() {
        return new NetworkUtils(mContext);
    }

    @Provides
    @Singleton
    @Named("SimpleService")
    public MoviesRepository providesSimpleMovieRepository(
            @Named("SimpleInterface") MovieApiService apiInterface, NetworkUtils networkUtils, DatabaseHelper databaseHelper) {
        return new MoviesRepository(apiInterface, networkUtils, databaseHelper);
    }

    @Provides
    @Singleton
    @Named("RxService")
    public MoviesRepository providesRxMovieRepository(
            @Named("RxInterface") MovieApiService apiInterface, NetworkUtils networkUtils, DatabaseHelper databaseHelper) {
        return new MoviesRepository(apiInterface, networkUtils, databaseHelper);
    }

}
