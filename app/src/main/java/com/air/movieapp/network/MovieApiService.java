/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.network;

import com.air.movieapp.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sagar on 20/8/16.
 */
public interface MovieApiService {

    @GET("movie/{category}")
    Call<Results> getMovies(@Path("category") String category, @Query("api_key") String apiKey, @Query("page") int page);

   /* @GET("movie/upcoming")
    Call<Results> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<Results> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);*/

}
