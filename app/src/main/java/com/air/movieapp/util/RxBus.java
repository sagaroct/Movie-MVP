package com.air.movieapp.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class RxBus {

    private PublishSubject<Object> bus = PublishSubject.create();

    public RxBus() {
    }

    public Observable<Object> toObservable() {
        return bus.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void send(Object o) {
        bus.onNext(o);
    }

}
