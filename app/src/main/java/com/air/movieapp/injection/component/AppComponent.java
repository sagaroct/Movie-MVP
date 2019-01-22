package com.air.movieapp.injection.component;

import com.air.movieapp.injection.module.AppModule;
import com.air.movieapp.injection.module.DataModule;
import com.air.movieapp.injection.module.HomeModule;
import com.air.movieapp.injection.module.MovieListModule;
import com.air.movieapp.injection.module.NetworkModule;
import com.air.movieapp.injection.module.RxBusModule;
import com.air.movieapp.ui.settings.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RxBusModule.class, DataModule.class})
public interface AppComponent {
    MovieListComponent plus(MovieListModule movieListModule);

    HomeComponent plus(HomeModule homeModule);

    void inject(SettingsActivity settingsActivity);

}
