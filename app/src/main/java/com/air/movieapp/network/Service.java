package com.air.movieapp.network;

import android.content.SharedPreferences;

import com.air.movieapp.common.NetworkUtils;
import com.air.movieapp.common.RestConstants;
import com.air.movieapp.data.DatabaseHelper;
import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;

import java.util.List;

/**
 * Created by sagar on 4/8/17.
 */

public class Service {

    private MovieApiService mMovieApiService;
    private NetworkUtils mNetworkUtils;
    private DatabaseHelper mDatabaseHelper;
    private CacheType mCacheType;
    private SharedPreferences mSharedPreferences;

    public Service(MovieApiService movieApiService, NetworkUtils networkUtils, DatabaseHelper databaseHelper) {
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
                mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                break;
            case CACHE:
                List<Movie> movies = mDatabaseHelper.getMovies(category);
                if (movies == null || movies.isEmpty()) {
                    mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                } else {
                    fetchMoviesFromCache(movies, page, callback);
                }
                break;
            case NETWORK_AND_CACHE:
                List<Movie> movies1 = mDatabaseHelper.getMovies(category);
                if (movies1 != null && !movies1.isEmpty()) {
                    fetchMoviesFromCache(movies1, page, callback);
                }
                mMovieApiService.getMovies(category, RestConstants.AP_KEY, page).enqueue(callback);
                break;
        }
    }

    private void fetchMoviesFromCache(List<Movie> movies, int page, ResponseCallback callback) {
        if (page > 1) return;
        Results results = new Results();
        results.setMovies(movies);
        callback.successFromDatabase(results);
    }

}
