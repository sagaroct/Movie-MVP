package com.air.movieapp.util;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

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
