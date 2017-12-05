package com.air.movieapp;

import com.air.movieapp.data.DataModule;
import com.air.movieapp.network.NetworkModule;
import com.air.movieapp.view.home.HomeComponent;
import com.air.movieapp.view.home.HomeModule;
import com.air.movieapp.view.movielist.MovieListComponent;
import com.air.movieapp.view.movielist.MovieListModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, DataModule.class})
public interface AppComponent {
    MovieListComponent plus(MovieListModule movieListModule);

    HomeComponent plus(HomeModule homeModule);

}
