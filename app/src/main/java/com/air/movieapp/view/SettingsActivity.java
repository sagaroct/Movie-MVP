package com.air.movieapp.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.air.movieapp.R;
import com.air.movieapp.adapter.SettingsAdapter;
import com.air.movieapp.common.CommonUtils;

/**
 * Created by sagar on 1/8/16.
 */
public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnItemClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private RecyclerView mRecyclerViewSettings;
    private SharedPreferences mSharedPreferences;
    private SettingsAdapter mSettingsAdapter;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.settings));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        mRecyclerViewSettings = (RecyclerView) findViewById(R.id.rv_settings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewSettings.setLayoutManager(mLayoutManager);
        mSettingsAdapter = new SettingsAdapter(getResources().getStringArray(R.array.settings_array));
        mSettingsAdapter.setmOnItemClickListener(this);
        mRecyclerViewSettings.setAdapter(mSettingsAdapter);
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
        if (string.equals(getString(R.string.date_format))) {
            CommonUtils.showDialogToChangeDateFormat(SettingsActivity.this, getString(R.string.date_format), getDateFormatCharSequences(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else {
            CommonUtils.showDialogToChangeDateFormat(SettingsActivity.this, getString(R.string.sort_date), getSortReleaseDateCharSequences(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
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
