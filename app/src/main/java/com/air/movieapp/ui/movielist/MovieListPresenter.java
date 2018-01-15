package com.air.movieapp.ui.movielist;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.air.movieapp.util.common.CommonUtils;
import com.air.movieapp.util.common.Constants;
import com.air.movieapp.util.common.NetworkUtils;
import com.air.movieapp.data.local.DatabaseHelper;
import com.air.movieapp.data.local.PreferenceHelper;
import com.air.movieapp.data.model.Movie;
import com.air.movieapp.data.model.Results;
import com.air.movieapp.network.CacheType;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.network.NetworkError;
import com.air.movieapp.network.ResponseCallback;
import com.air.movieapp.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import rx.Observer;

/**
 * Created by sagar on 10/8/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private MovieListContract.View mView;
    private DatabaseHelper mDatabaseHelper;
    private NetworkUtils mNetworkUtils;
    private MoviesRepository mMoviesRepository;
    private LinearLayoutManager mLinearLayoutManager;
    private String mCategory;
    private int mPageCount;
    private PreferenceHelper mPreferenceHelper;
    private List<Movie> mMovies = new ArrayList<>();
    private RxBus mRxBus;


    public MovieListPresenter(MoviesRepository moviesRepository, MovieListContract.View mView, LinearLayoutManager linearLayoutManager
            , DatabaseHelper mDatabaseHelper, NetworkUtils mNetworkUtils, PreferenceHelper preferenceHelper, RxBus rxBus) {
        this.mMoviesRepository = moviesRepository;
        this.mView = mView;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mNetworkUtils = mNetworkUtils;
        this.mLinearLayoutManager = linearLayoutManager;
        this.mPreferenceHelper = preferenceHelper;
        this.mRxBus = rxBus;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onCreateView() {
        mRxBus.toObservable().subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Object object) {
                if (object instanceof Constants.DateFormat) {
                    if(object.equals(Constants.DateFormat.MONTH_FIRST)){
                        changeDateFormat(Constants.MONTH_FIRST);
                    }else{
                        changeDateFormat(Constants.YEAR_FIRST);
                    }
                }
            }
        });
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
    public void initScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnScrollListener(provideMovieListScrollListener());
    }

    @Override
    public void fetchMovies(String category) {
        mCategory = category;
        if (!mNetworkUtils.isNetworkConnected()) {
            mView.showNoInternetDialog();
            mView.showEmptyView();
            return;
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
        mMoviesRepository.setCacheType(mNetworkUtils.setCacheType(CacheType.NETWORK_AND_CACHE));
        mMoviesRepository.getMovies(categoryName, current_page, new ResponseCallback<Results>() {
            @Override
            public void successFromNetwork(Results results) {
                mView.showProgress(false);
                if(results.getMovies().size()>0) {
                    results.setMovies(changeDateFormatForApiResults(results.getMovies()
                            , mPreferenceHelper.getStringFromSharedPreference(Constants.DATE_FORMAT, Constants.MONTH_FIRST)));
                    updateMovieList(results, current_page);
                    mDatabaseHelper.saveMovieList(categoryName, results.getMovies());
                }else{
                    mView.showEmptyView();
                }
            }

            @Override
            public void successFromDatabase(Results results) {
                mView.showProgress(false);
                results.setMovies(changeDateFormatForDbResults( mDatabaseHelper.getRealm().copyFromRealm(results.getMovies())
                        , mPreferenceHelper.getStringFromSharedPreference(Constants.DATE_FORMAT, Constants.MONTH_FIRST)));
                updateMovieList(results, current_page);
            }

            @Override
            public void failure(NetworkError error) {
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
        Log.d(TAG, "changeDateFormat: "+strFormat);
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
