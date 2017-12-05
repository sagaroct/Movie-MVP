package com.air.movieapp.view.home;

import dagger.Subcomponent;


@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
