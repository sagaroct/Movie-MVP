package com.air.movieapp.injection.module;

import com.air.movieapp.ui.home.HomeContract;
import com.air.movieapp.ui.home.HomePresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class HomeModule {

    private HomeContract.View mHomeView;

    public HomeModule(HomeContract.View homeView) {
        this.mHomeView = homeView;
    }

    @Provides
    public HomePresenter provideHomePresenter() {
        return new HomePresenter(mHomeView);
    }
}
