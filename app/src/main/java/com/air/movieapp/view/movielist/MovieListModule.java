package com.air.movieapp.view.movielist;

import android.support.v7.widget.LinearLayoutManager;

import com.air.movieapp.adapter.MovieListAdapter;
import com.air.movieapp.data.DatabaseHelper;
import com.air.movieapp.data.PreferenceHelper;
import com.air.movieapp.model.Movie;
import com.air.movieapp.common.NetworkUtils;
import com.air.movieapp.network.MoviesRepository;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sagar on 10/8/17.
 */

@Module
public class MovieListModule {

    private MovieListContract.View mView;

    public MovieListModule(MovieListContract.View view) {
        this.mView = view;
    }

    @Provides
    public MovieListAdapter provideMovieListAdapter(){
        return new MovieListAdapter(new ArrayList<Movie>());
    }

    @Provides
    public MovieListPresenter provideMovieListPresenter(MoviesRepository moviesRepository, LinearLayoutManager linearLayoutManager
            , DatabaseHelper databaseHelper, NetworkUtils networkUtils, PreferenceHelper preferenceHelper){
        return new MovieListPresenter(moviesRepository, mView, linearLayoutManager, databaseHelper, networkUtils, preferenceHelper);
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(mView.getContext());
    }

}
