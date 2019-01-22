package com.air.movieapp.movielist;

import android.support.v7.widget.LinearLayoutManager;

import com.air.movieapp.util.common.Constants;
import com.air.movieapp.util.common.NetworkUtils;
import com.air.movieapp.data.local.DatabaseHelper;
import com.air.movieapp.data.local.PreferenceHelper;
import com.air.movieapp.data.model.Movie;
import com.air.movieapp.data.model.Results;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.network.ResponseCallback;
import com.air.movieapp.util.RxBus;
import com.air.movieapp.ui.movielist.MovieListContract;
import com.air.movieapp.ui.movielist.MovieListPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sagar on 14/12/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterTest {

    private List<Movie> MOVIES = Arrays.asList(new Movie(123, "someTitle", "1992-12-10"
        , "soneOverView", 1.00f, Constants.POPULAR ));

    private Results results = new Results();

    private static List<Movie> EMPTY_MOVIES = new ArrayList<>(0);

    @Mock
    private DatabaseHelper mDatabaseHelper;

    @Mock
    private NetworkUtils mNetworkUtils;

    @Mock
    private LinearLayoutManager mLinearLayoutManager;

    @Mock
    private PreferenceHelper mPreferenceHelper;

    @Mock
    private MoviesRepository mMoviesRepository;

    @Mock
    private MovieListContract.View mView;

    @Mock
    private RxBus mRxBus;

    @Captor
    private ArgumentCaptor<ResponseCallback> mLoadMoviesCallbackCaptor;

    private MovieListPresenter movieListPresenter;

    @Before
    public void setupMovieListPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
//        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mPreferenceHelper.saveStringIntoSharedPreference(Constants.DATE_FORMAT, Constants.MONTH_FIRST);
        movieListPresenter = new MovieListPresenter(mMoviesRepository, mView, mLinearLayoutManager, mDatabaseHelper, mNetworkUtils, mPreferenceHelper, mRxBus);
    }

    @Test
    public void loadMoviesFromRepositoryAndLoadIntoView() {
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
        when(mNetworkUtils.isNetworkConnected()).thenReturn(true);
        movieListPresenter.fetchMovies(Constants.POPULAR);

        // Callback is captured and invoked with stubbed movies
        verify(mMoviesRepository).getMovies(eq(Constants.POPULAR), eq(1), mLoadMoviesCallbackCaptor.capture());
//        mLoadMoviesCallbackCaptor.getValue().successFromDatabase(results);
        results.setMovies(MOVIES);
        mLoadMoviesCallbackCaptor.getValue().successFromNetwork(results);

        // Then progress indicator is hidden and movies are shown in UI.
        verify(mView, atMost(2)).showProgress(false);
        verify(mView, atMost(2)).showList(MOVIES);
    }

    @Test
    public void loadEmptyMoviesIntoView() {
        when(mNetworkUtils.isNetworkConnected()).thenReturn(true);
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
        movieListPresenter.fetchMovies(Constants.POPULAR);

        // Callback is captured and invoked with stubbed movies
        verify(mMoviesRepository).getMovies(eq(Constants.POPULAR), eq(1), mLoadMoviesCallbackCaptor.capture());
        results.setMovies(EMPTY_MOVIES);
        mLoadMoviesCallbackCaptor.getValue().successFromNetwork(results);

        // Then progress indicator is hidden and emptyview is shown in UI.
        verify(mView, atMost(2)).showProgress(false);
        verify(mView).showEmptyView();
    }

    @Test
    public void loadMoviesWithNoInternetError() {
        // Given an initialized MovieListPresenter with initialized movies
        // When loading of Movies is requested
        when(mNetworkUtils.isNetworkConnected()).thenReturn(false);
        movieListPresenter.fetchMovies(Constants.POPULAR);
//        assertEquals(true, mNetworkUtils.isNetworkConnected());
//        doReturn(false).when(mNetworkUtils).isNetworkConnected();

        // No internet toast shown and emptyview displayed.
        verify(mView).showNoInternetDialog();
//        verify(mView).showEmptyView();
    }
}
