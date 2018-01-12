package com.air.movieapp;

import com.air.movieapp.data.DataModule;
import com.air.movieapp.network.NetworkModule;
import com.air.movieapp.rxbus.RxBusModule;
import com.air.movieapp.view.home.HomeComponent;
import com.air.movieapp.view.home.HomeModule;
import com.air.movieapp.view.movielist.MovieListComponent;
import com.air.movieapp.view.movielist.MovieListModule;
import com.air.movieapp.view.settings.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RxBusModule.class, DataModule.class})
public interface AppComponent {
    MovieListComponent plus(MovieListModule movieListModule);

    HomeComponent plus(HomeModule homeModule);

    void inject(SettingsActivity settingsActivity);

}
