package com.air.movieapp.view.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.air.movieapp.R;
import com.air.movieapp.common.CommonUtils;
import com.air.movieapp.view.movielist.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagar on 21/8/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View mView;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
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

    public void setAdapterToViewPager(ViewPager mViewPager, FragmentManager fragmentManager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        MovieListFragment topratedMovieFragment = new MovieListFragment();
        topratedMovieFragment.setArguments(CommonUtils.getBundleWithValue(0));
        adapter.addFragment(topratedMovieFragment, mView.getContext().getString(R.string.top_rated));
        MovieListFragment upcomingMovieFragment = new MovieListFragment();
        upcomingMovieFragment.setArguments(CommonUtils.getBundleWithValue(1));
        adapter.addFragment(upcomingMovieFragment, mView.getContext().getString(R.string.upcoming));
        MovieListFragment popularMovieFragment = new MovieListFragment();
        popularMovieFragment.setArguments(CommonUtils.getBundleWithValue(2));
        adapter.addFragment(popularMovieFragment, mView.getContext().getString(R.string.popular));
        mViewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<MovieListFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MovieListFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MovieListFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
