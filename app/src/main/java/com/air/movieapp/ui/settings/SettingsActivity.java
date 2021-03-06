package com.air.movieapp.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.air.movieapp.R;
import com.air.movieapp.application.MovieApplication;
import com.air.movieapp.data.local.PreferenceHelper;
import com.air.movieapp.util.RxBus;
import com.air.movieapp.util.common.CommonUtils;
import com.air.movieapp.util.common.Constants;

import javax.inject.Inject;

/**
 * Created by sagar on 1/8/16.
 */
public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnItemClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Inject
    PreferenceHelper mPreferenceHelper;

    @Inject
    RxBus mRxBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MovieApplication.get(this).getAppComponent().inject(this);
        initViews();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.settings));
        RecyclerView mRecyclerViewSettings = (RecyclerView) findViewById(R.id.rv_settings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewSettings.setLayoutManager(mLayoutManager);
        SettingsAdapter mSettingsAdapter = new SettingsAdapter(getResources().getStringArray(R.array.settings_array));
        mSettingsAdapter.setOnItemClickListener(this);
        mRecyclerViewSettings.setAdapter(mSettingsAdapter);
        mRecyclerViewSettings.setHasFixedSize(true);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException nPE) {
            nPE.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(String string) {
        openSettingsPopup(string);
    }

    private void openSettingsPopup(String string) {
        int dateFormatPref = mPreferenceHelper.getStringFromSharedPreference(Constants.DATE_FORMAT, Constants.MONTH_FIRST)
                .equals(Constants.MONTH_FIRST)
                ? 0 : 1;
        int releaseDateSortPref = mPreferenceHelper.getStringFromSharedPreference(Constants.RELEASE_DATE, Constants.ASCENDING)
                .equals(Constants.ASCENDING)
                ? 0 : 1;
        if (string.equals(getString(R.string.date_format))) {
            CommonUtils.showDialogToChangeDateFormat(SettingsActivity.this, getString(R.string.date_format), getDateFormatCharSequences(), dateFormatPref, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int pos) {
                    if(pos == 0){
                        mPreferenceHelper.saveStringIntoSharedPreference(Constants.DATE_FORMAT, Constants.MONTH_FIRST);
                        mRxBus.send(Constants.DateFormat.MONTH_FIRST);
                    }else{
                        mPreferenceHelper.saveStringIntoSharedPreference(Constants.DATE_FORMAT, Constants.YEAR_FIRST);
                        mRxBus.send(Constants.DateFormat.YEAR_FIRST);
                    }
                    dialogInterface.dismiss();
                }
            });
        } else {
            CommonUtils.showDialogToChangeDateFormat(SettingsActivity.this, getString(R.string.sort_date), getSortReleaseDateCharSequences(), releaseDateSortPref, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int pos) {
                    if(pos == 0){
                        mPreferenceHelper.saveStringIntoSharedPreference(Constants.RELEASE_DATE, Constants.ASCENDING);
                    }else{
                        mPreferenceHelper.saveStringIntoSharedPreference(Constants.RELEASE_DATE, Constants.DESCENDING);
                    }
                    dialogInterface.dismiss();
                }
            });
        }
    }

    @NonNull
    private CharSequence[] getDateFormatCharSequences() {
        CharSequence[] charSequence = new CharSequence[2];
        charSequence[0] = getString(R.string.month_day_yr);
        charSequence[1] = getString(R.string.yr_month_day);
        return charSequence;
    }

    @NonNull
    private CharSequence[] getSortReleaseDateCharSequences() {
        CharSequence[] charSequence = new CharSequence[2];
        charSequence[0] = getString(R.string.ascending);
        charSequence[1] = getString(R.string.descending);
        return charSequence;
    }

}
