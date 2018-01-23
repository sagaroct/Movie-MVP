package com.air.movieapp.network;

import android.content.SharedPreferences;

import com.air.movieapp.data.local.DatabaseHelper;
import com.air.movieapp.data.model.Movie;
import com.air.movieapp.data.model.Results;
import com.air.movieapp.util.common.NetworkUtils;
import com.air.movieapp.util.common.RestConstants;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sagar on 4/8/17.
 */

public class MoviesRepository {

    private MovieApiService mMovieApiService;
    private NetworkUtils mNetworkUtils;
    private DatabaseHelper mDatabaseHelper;
    private CacheType mCacheType;
    private SharedPreferences mSharedPreferences;

    public MoviesRepository(MovieApiService movieApiService, NetworkUtils networkUtils, DatabaseHelper databaseHelper) {
        this.mMovieApiService = movieApiService;
        this.mNetworkUtils = networkUtils;
        this.mDatabaseHelper = databaseHelper;
        this.mCacheType = CacheType.NETWORK;
    }

    public void setCacheType(CacheType cacheType){
        this.mCacheType = cacheType;
    }

    public void getMovies(String category, int page, ResponseCallback callback) {
        switch (mCacheType) {
            case NETWORK:
//                mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                getMoviesFromNetwork(category, page, callback);
                break;
            case CACHE:
                List<Movie> movies = mDatabaseHelper.getMovies(category);
                if (movies == null || movies.isEmpty()) {
//                    mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                    getMoviesFromNetwork(category, page, callback);
                } else {
                    fetchMoviesFromCache(movies, page, callback);
                }
                break;
            case NETWORK_AND_CACHE:
                List<Movie> movies1 = mDatabaseHelper.getMovies(category);
                if (movies1 != null && !movies1.isEmpty()) {
                    fetchMoviesFromCache(movies1, page, callback);
                }
//                mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                getMoviesFromNetwork(category, page, callback);
                break;
        }
    }

    /**
     * RxJava Parsing
     */
    private void getMoviesFromNetwork(String category, int page, final ResponseCallback callback) {
        mMovieApiService.getMovies(category, RestConstants.AP_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Results>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        callback.failure(new NetworkError(e));
                    }

                    @Override
                    public void onNext(Results cityListResponse) {
                        callback.successFromNetwork(cityListResponse);
                    }
                });
    }

    private void fetchMoviesFromCache(List<Movie> movies, int page, ResponseCallback callback) {
        if (page > 1) return;
        Results results = new Results();
        results.setMovies(movies);
        callback.successFromDatabase(results);
    }

}
