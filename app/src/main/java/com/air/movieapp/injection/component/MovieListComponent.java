package com.air.movieapp.injection.component;

import com.air.movieapp.injection.module.MovieListModule;
import com.air.movieapp.ui.movielist.MovieListFragment;

import dagger.Subcomponent;

/**
 * Created by sagar on 10/8/17.
 */

@Subcomponent(modules = MovieListModule.class)
public interface MovieListComponent {
    void inject(MovieListFragment movieListFragment);


}
