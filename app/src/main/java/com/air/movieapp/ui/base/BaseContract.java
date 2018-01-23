package com.air.movieapp.ui.base;

import android.content.Context;

public interface BaseContract {

    interface View {

        void showProgress(boolean status);

        Context getContext();

        void showNoInternetDialog();
    }

    interface Presenter {

        void onCreate();

        void onResume();

        void onPause();

        void onDestroy();
    }
} 