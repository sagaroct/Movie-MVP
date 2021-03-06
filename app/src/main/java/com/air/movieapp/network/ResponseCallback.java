package com.air.movieapp.network;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;


public abstract class ResponseCallback<T> implements Callback<T> {

    public abstract void successFromNetwork(T t);

    public abstract void successFromDatabase(T t);

    //To be used for Retrofit without RxJava
//    public abstract void failure(Call<T> call, NetworkError error);

    public abstract void failure(NetworkError error);

    public abstract void onTimeOut(Call<T> call);

    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        if (response != null && response.isSuccessful()) {
            successFromNetwork(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            onTimeOut(call);
        } else {
            failure(new NetworkError(t));
        }
    }
}
