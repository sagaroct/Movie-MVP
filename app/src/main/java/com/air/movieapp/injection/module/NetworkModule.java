package com.air.movieapp.injection.module;

import android.content.Context;

import com.air.movieapp.BuildConfig;
import com.air.movieapp.data.local.DatabaseHelper;
import com.air.movieapp.network.MovieApiService;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.util.common.NetworkUtils;
import com.air.movieapp.util.common.RestConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

//    private Context mContext;

 /*   public NetworkModule() {

    }*/

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
    public NetworkUtils provideNetworkUtils(Context context) {
        return new NetworkUtils(context);
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
