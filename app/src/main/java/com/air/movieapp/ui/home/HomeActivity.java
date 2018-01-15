
/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */
package com.air.movieapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.air.movieapp.application.MovieApplication;
import com.air.movieapp.R;
import com.air.movieapp.util.common.Constants;
import com.air.movieapp.injection.module.HomeModule;
import com.air.movieapp.network.CacheType;
import com.air.movieapp.network.MoviesRepository;
import com.air.movieapp.ui.base.BaseActivity;
import com.air.movieapp.ui.settings.SettingsActivity;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Main Container where all movie fragments are added
 */
public class HomeActivity extends BaseActivity implements HomeContract.View {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    @Named("RxService")
    MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mHomePresenter.setAdapterToViewPager(mViewPager, getSupportFragmentManager());
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setNavigationDrawer();
        moviesRepository.setCacheType(CacheType.CACHE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_share:
                break;
            case R.id.menu_settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.SETTINGS_REQUEST_CODE){

        }
    }

    private void setNavigationDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int pos = 0;
                switch (menuItem.getItemId()) {
                    case R.id.item_top_rated:
                        pos = 0;
                        break;
                    case R.id.item_upcoming:
                        pos = 1;
                        break;
                    case R.id.item_popular:
                        pos = 2;
                        break;
                }
                mViewPager.setCurrentItem(pos, true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void setupActivityComponent() {
        MovieApplication.get(this).getAppComponent().plus(new HomeModule(this)).inject(this);
    }

    @Override
    public void showProgress(boolean status) {
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showPopupMessage(String title, String message) {
    }

    @Override
    public void showNoInternetDialog() {
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
