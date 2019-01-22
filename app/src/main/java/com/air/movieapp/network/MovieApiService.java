/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.network;

import com.air.movieapp.data.model.Results;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sagar on 20/8/16.
 */
public interface MovieApiService {

    @GET("movie/{category}")
    Observable<Results> getMovies(@Path("category") String category, @Query("api_key") String apiKey, @Query("page") int page);

}
