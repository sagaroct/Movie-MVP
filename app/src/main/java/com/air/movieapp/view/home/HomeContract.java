package com.air.movieapp.view.home;


import com.air.movieapp.view.base.BaseContract;

/**
 * Created by shreesha on 30/12/16.
 */

public interface HomeContract {
    interface View extends BaseContract.View {
        void setActionBarTitle(String title);

        void closeLeftDrawer();

        void loadMovieFragment(String category);

        void updateLeftDrawer(int position);
    }

    interface Presenter extends BaseContract.Presenter {

    }

}
