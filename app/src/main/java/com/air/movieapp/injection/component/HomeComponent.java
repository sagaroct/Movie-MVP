package com.air.movieapp.injection.component;

import com.air.movieapp.injection.module.HomeModule;
import com.air.movieapp.ui.home.HomeActivity;

import dagger.Subcomponent;


@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
