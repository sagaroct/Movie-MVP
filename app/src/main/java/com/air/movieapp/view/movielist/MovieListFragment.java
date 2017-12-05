/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.view.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.air.movieapp.MovieApplication;
import com.air.movieapp.R;
import com.air.movieapp.adapter.MovieListAdapter;
import com.air.movieapp.common.Constants;
import com.air.movieapp.network.Service;
import com.air.movieapp.view.base.BaseFragment;

import javax.inject.Inject;

/**
 * Common fragment for all movie listing
 */
public class MovieListFragment extends BaseFragment implements MovieListContract.View {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    private ProgressBar mProgressBar;
    private RecyclerView mMoviesRecyclerView;
    private String mType;

    @Inject
    MovieListPresenter mMovieListPresenter;

    @Inject
    MovieListAdapter mMovieListAdapter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    Service mService;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        mType = args.getString(Constants.TAB);
    }

    @Override
    protected void setupFragmentComponent() {
        MovieApplication.get(getActivity()).getAppComponent().plus(new MovieListModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie);
        mMovieListPresenter.initAdapter(mMoviesRecyclerView, mLinearLayoutManager);
        mMovieListPresenter.fetchMovies(mType, mService);
        return view;
    }

    @Override
    public void showProgress(boolean status) {
        if(status){
            mProgressBar.setVisibility(View.VISIBLE);
        }else{
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPopupMessage(String title, String message) {
    }

    @Override
    public void showNoInternetDialog() {
    }

    @Override
    public void showEmptyView() {
    }
}
