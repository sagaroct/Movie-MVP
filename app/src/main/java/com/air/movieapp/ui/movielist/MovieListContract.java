package com.air.movieapp.ui.movielist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.air.movieapp.data.model.Movie;
import com.air.movieapp.ui.base.BaseContract;

import java.util.List;

/**
 * Created by sagar on 10/8/17.
 */

public interface MovieListContract  {

    interface View extends BaseContract.View{
        void showEmptyView();

        void showList(List<Movie> movies);

        void addAndShowList(List<Movie> movies);

        void sortList(String prefValue);

        void refreshList();
    }

    interface Presenter extends BaseContract.Presenter{
        void onCreateView();

        void initScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager);

        void fetchMovies(String category);
    }

}
