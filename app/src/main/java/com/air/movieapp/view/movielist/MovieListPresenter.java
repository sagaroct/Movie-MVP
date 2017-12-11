package com.air.movieapp.view.movielist;

import android.content.SharedPreferences;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.air.movieapp.common.CommonUtils;
import com.air.movieapp.common.Constants;
import com.air.movieapp.common.NetworkUtils;
import com.air.movieapp.data.DatabaseHelper;
import com.air.movieapp.data.PreferenceHelper;
import com.air.movieapp.model.Movie;
import com.air.movieapp.model.Results;
import com.air.movieapp.network.CacheType;
import com.air.movieapp.network.NetworkError;
import com.air.movieapp.network.ResponseCallback;
import com.air.movieapp.network.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by sagar on 10/8/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private MovieListContract.View mView;
    private DatabaseHelper mDatabaseHelper;
    private NetworkUtils mNetworkUtils;
    private Service mService;
    private LinearLayoutManager mLinearLayoutManager;
    private String mCategory;
    private int mPageCount;
    private PreferenceHelper mPreferenceHelper;
    private SharedPreferences.OnSharedPreferenceChangeListener mSharedePreferenceListener;
    private List<Movie> mMovies = new ArrayList<>();


    public MovieListPresenter(MovieListContract.View mView, LinearLayoutManager linearLayoutManager
            , DatabaseHelper mDatabaseHelper, NetworkUtils mNetworkUtils, PreferenceHelper preferenceHelper) {
        this.mView = mView;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mNetworkUtils = mNetworkUtils;
        this.mLinearLayoutManager = linearLayoutManager;
        this.mPreferenceHelper = preferenceHelper;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onCreateView() {
        mSharedePreferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefKey) {
                if(prefKey.equalsIgnoreCase(Constants.DATE_FORMAT) || prefKey.equalsIgnoreCase(Constants.RELEASE_DATE)){
                    mView.sortList(sharedPreferences.getString(prefKey,""));
                    switch (sharedPreferences.getString(prefKey,"")){
                        case Constants.MONTH_FIRST:
                            changeDateFormat(Constants.MONTH_FIRST);
                            break;
                        case Constants.YEAR_FIRST:
                            changeDateFormat(Constants.YEAR_FIRST);
                            break;
                        case Constants.ASCENDING:
                            //TODO: Will be done in future.
                            break;
                        case Constants.DESCENDING:
                            //TODO: Will be done in future.
                            break;
                        default:break;
                    }
                }
            }
        };
    }

    @Override
    public void onResume() {
        mPreferenceHelper.getSharedpreferences().registerOnSharedPreferenceChangeListener(mSharedePreferenceListener);
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        mPreferenceHelper.getSharedpreferences().unregisterOnSharedPreferenceChangeListener(mSharedePreferenceListener);
    }

    @Override
    public void initScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnScrollListener(provideMovieListScrollListener());
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
                results.setMovies(changeDateFormatForApiResults(results.getMovies()
                        , mPreferenceHelper.getSharedpreferences().getString(Constants.DATE_FORMAT, Constants.MONTH_FIRST)));
                updateMovieList(results, current_page);
                mDatabaseHelper.saveMovieList(categoryName, results.getMovies());
            }

            @Override
            public void successFromDatabase(Results results) {
                mView.showProgress(false);
                results.setMovies(changeDateFormatForDbResults( mDatabaseHelper.getRealm().copyFromRealm(results.getMovies())
                        , mPreferenceHelper.getSharedpreferences().getString(Constants.DATE_FORMAT, Constants.MONTH_FIRST)));
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
        mMovies.addAll(results.getMovies());
        if (current_page > 1) {
            mView.addAndShowList(mMovies);
        } else {
            mView.showList(mMovies);
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

    private void changeDateFormat(String strFormat){
        mDatabaseHelper.getRealm().beginTransaction();
        for(Movie movie: mMovies){
            movie.setReleaseDate(CommonUtils.convertMovieDateFormat(movie.getReleaseDate(), strFormat));
        }
        mDatabaseHelper.updateMovieList(mMovies);
        mDatabaseHelper.getRealm().commitTransaction();
        mView.refreshList();
    }

    private List<Movie> changeDateFormatForDbResults(List<Movie> movies, String strFormat) {
        mDatabaseHelper.getRealm().beginTransaction();
        for(Movie movie: movies){
            movie.setReleaseDate(CommonUtils.convertMovieDateFormat(movie.getReleaseDate(), strFormat));
        }
        mDatabaseHelper.updateMovieList(movies);
        mDatabaseHelper.getRealm().commitTransaction();
        return movies;
    }

    private List<Movie> changeDateFormatForApiResults(List<Movie> movies, String strFormat) {
        for(Movie movie: movies){
            movie.setReleaseDate(CommonUtils.convertMovieDateFormatForApiResults(movie.getReleaseDate(), strFormat));
        }
        return movies;
    }
}
