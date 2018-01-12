package com.air.movieapp.rxbus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RxBusModule {

    public RxBusModule() {
    }

    @Provides
    @Singleton
    public RxBus providesRxBus() {
        return new RxBus();
    }

}
