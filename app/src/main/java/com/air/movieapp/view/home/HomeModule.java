package com.air.movieapp.view.home;

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
