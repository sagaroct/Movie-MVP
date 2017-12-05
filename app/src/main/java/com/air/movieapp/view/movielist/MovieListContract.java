package com.air.movieapp.view.movielist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.air.movieapp.network.Service;
import com.air.movieapp.view.base.BaseContract;

/**
 * Created by sagar on 10/8/17.
 */

public interface MovieListContract  {

    interface View extends BaseContract.View{
        void showEmptyView();
    }

    interface Presenter extends BaseContract.Presenter{
        void initAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager);

        void fetchMovies(String category, Service service);
    }

}
