/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.ui.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.air.movieapp.R;
import com.air.movieapp.application.MovieApplication;
import com.air.movieapp.data.model.Movie;
import com.air.movieapp.injection.module.MovieListModule;
import com.air.movieapp.ui.base.BaseFragment;
import com.air.movieapp.util.common.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Common fragment for all movie listing
 */
public class MovieListFragment extends BaseFragment implements MovieListContract.View {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    private ProgressBar mProgressBar;
    private RecyclerView mMoviesRecyclerView;
    private TextView mTvEmptyView;
    private String mType;

    @Inject
    MovieListPresenter mMovieListPresenter;

    @Inject
    MovieListAdapter mMovieListAdapter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

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
        initViews(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        mMovieListPresenter.onDestroyView();
        super.onDestroyView();
    }

    private void initViews(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movie);
        mTvEmptyView = (TextView) view.findViewById(R.id.tv_empty_view);
        mMovieListPresenter.onCreateView();
        mMoviesRecyclerView.setAdapter(mMovieListAdapter);
        mMovieListPresenter.initScrollListener(mMoviesRecyclerView, mLinearLayoutManager);
        mMovieListPresenter.fetchMovies(mType);
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
    public void showNoInternetDialog() {
        Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        mTvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        mTvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(List<Movie> movies) {
        mTvEmptyView.setVisibility(View.GONE);
        mMovieListAdapter.setData(movies);
    }

    @Override
    public void addAndShowList(List<Movie> movies) {
        mMovieListAdapter.addData(movies);
    }

    @Override
    public void sortList(String prefValue) {
        //Incomplete, should be completed.
        switch (prefValue){
            case Constants.MONTH_FIRST:
                break;
            case Constants.YEAR_FIRST:
                break;
            case Constants.ASCENDING:
                break;
            case Constants.DESCENDING:
                break;
            default:break;
        }
    }

    @Override
    public void refreshList(List<Movie> movies) {
//        mMovieListAdapter.notifyDataSetChanged();
        mMovieListAdapter.update((ArrayList<Movie>) movies);
    }

    @Override
    public void refreshList() {
        mMovieListAdapter.notifyDataSetChanged();
    }
}
