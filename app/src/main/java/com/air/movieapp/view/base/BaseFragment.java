package com.air.movieapp.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by shreesha on 4/1/17.
 */

public abstract class BaseFragment extends Fragment {

    protected IFragmentInteractionListener mFragmentInteractionListener;

    /**
     * enum for set fragment transaction type
     */
    public enum FragmentTransactionType {
        ADD,
        REPLACE,
        ADD_TO_BACK_STACK_AND_REPLACE,
        POP_BACK_STACK_AND_REPLACE,
        CLEAR_BACK_STACK_AND_REPLACE,
        ADD_TO_BACK_STACK_AND_ADD
    }

    /**
     * enum for identify fragment
     */
    public enum FragmentType {
        PHOTO_LIST,
        PHOTO_DETAIL
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentInteractionListener = (IFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent();
    }

    protected abstract void setupFragmentComponent();
}
