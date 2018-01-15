package com.air.movieapp.injection.module;

import com.air.movieapp.util.RxBus;

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
