package com.air.movieapp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shreesha on 29/12/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements IFragmentInteractionListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
    }

    protected abstract void setupActivityComponent();

  /*  @Override
    public void loadFragment(int fragmentContainerId, BaseFragment fragment, @Nullable String tag, int enterAnimId, int exitAnimId, BaseFragment.FragmentTransactionType fragmentTransactionType) {
        performFragmentTranscation(fragmentContainerId, fragment, tag,
                (enterAnimId == 0) ? 0 : enterAnimId,
                (exitAnimId == 0) ? 0 : exitAnimId,
                fragmentTransactionType);
    }*/



    @Override
    public void loadFragment(int fragmentContainerId, BaseFragment fragment) {
        performFragmentTranscation(fragmentContainerId, fragment, null,
                0, 0, BaseFragment.FragmentTransactionType.ADD);
    }

    private void performFragmentTranscation(int fragmentContainerId,
                                            Fragment fragment, String tag,
                                            int enterAnimId, int exitAnimId,
                                            BaseFragment.FragmentTransactionType fragmentTransactionType) {

        switch (fragmentTransactionType) {
            case ADD:
                addFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case REPLACE:
                replaceFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case ADD_TO_BACK_STACK_AND_ADD:
                addToBackStackAndAdd(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case ADD_TO_BACK_STACK_AND_REPLACE:
                addToBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case POP_BACK_STACK_AND_REPLACE:
                popBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case CLEAR_BACK_STACK_AND_REPLACE:
                clearBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            default:
                replaceFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
        }

    }

    private void addToBackStackAndAdd(int fragmentContainerId, Fragment fragment, String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, 0, 0, exitAnimId)
                .add(fragmentContainerId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    protected void addFragment(int fragmentContainerId, Fragment fragment, String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .add(fragmentContainerId, fragment, tag)
                .commit();
    }

    private void replaceFragment(int fragmentContainerId, Fragment fragment, String tag,
                                 int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void popBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                        String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void addToBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                          String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .setCustomAnimations(enterAnimId, 0, 0, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void clearBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                          String tag, int enterAnimId, int exitAnimId) {
        clearBackStack(FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void clearBackStack(int flag) {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fm.getBackStackEntryAt(0);
            fm.popBackStack(first.getId(), flag);
        }
    }



}
