package com.air.movieapp.view.movielist;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.air.movieapp.adapter.MovieListAdapter;
import com.air.movieapp.common.Constants;
import com.air.movieapp.data.DatabaseHelper;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.CacheType;
import com.air.movieapp.network.NetworkError;
import com.air.movieapp.common.NetworkUtils;
import com.air.movieapp.network.ResponseCallback;
import com.air.movieapp.network.Service;

import retrofit2.Call;

/**
 * Created by sagar on 10/8/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private RecyclerView mRecyclerView;
    private MovieListContract.View mView;
    private MovieListAdapter mMovieListAdapter;
    private DatabaseHelper mDatabaseHelper;
    private NetworkUtils mNetworkUtils;
    private Service mService;
    private LinearLayoutManager mLinearLayoutManager;
    private String mCategory;
    private int mPageCount;

    public MovieListPresenter(MovieListContract.View mView, MovieListAdapter movieListAdapter, LinearLayoutManager linearLayoutManager, DatabaseHelper mDatabaseHelper, NetworkUtils mNetworkUtils) {
        this.mView = mView;
        this.mMovieListAdapter = movieListAdapter;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mNetworkUtils = mNetworkUtils;
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void initAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.mRecyclerView = recyclerView;
        this.mLinearLayoutManager = linearLayoutManager;
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMovieListAdapter);
        mRecyclerView.addOnScrollListener(provideMovieListScrollListener());
    }

    @Override
    public void fetchMovies(String category, Service service) {
        mCategory = category;
        mService = service;
        if (mNetworkUtils.isNetworkConnected()) {
            mView.showNoInternetDialog();
        }
        if (TextUtils.isEmpty(category)) {
            mView.showEmptyView();
        } else {
            getMovies(category, 1);
//            setPagination();
        }
    }

    private void getMovies(final String categoryName, final int current_page) {
        mView.showProgress(true);
        mService.setCacheType(mNetworkUtils.setCacheType(CacheType.NETWORK_AND_CACHE));
        mService.getMovies(categoryName, current_page, new ResponseCallback<Results>() {
            @Override
            public void successFromNetwork(Results results) {
                mView.showProgress(false);
                updateMovieList(results, current_page);
                mDatabaseHelper.saveMovieList(categoryName, results.getMovies());
            }

            @Override
            public void successFromDatabase(Results results) {
                mView.showProgress(false);
                updateMovieList(results, current_page);
            }


            @Override
            public void failure(Call call, NetworkError error) {
                mView.showProgress(false);
            }

            @Override
            public void onTimeOut(Call call) {
                mView.showProgress(false);
            }
        });
    }

    private void updateMovieList(Results results, int current_page) {
        if (current_page > 1) {
            mMovieListAdapter.addData(results.getMovies());
        } else {
            mMovieListAdapter.setData(results.getMovies());
        }
    }

    private RecyclerView.OnScrollListener provideMovieListScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= Constants.PAGE_SIZE) {
                        getMovies(mCategory, ++mPageCount);
                    }
                }
            }
        };
    }
}
