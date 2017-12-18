package com.air.movieapp.movies;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.air.movieapp.R;
import com.air.movieapp.view.settings.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by sagar on 18/12/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsScreenTest {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityTestRule =
            new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void settingsListItemTapped(){
        // First item i.e Date Format in settings screen is tapped
        onView(withId(R.id.rv_settings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(R.string.date_format)).check(matches(isDisplayed()));
    }


}
