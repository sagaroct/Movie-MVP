package com.air.movieapp.movies;

import android.content.ComponentName;
import android.support.annotation.UiThread;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.air.movieapp.R;
import com.air.movieapp.view.home.HomeActivity;
import com.air.movieapp.view.settings.SettingsActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by sagar on 15/12/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);



    @Test
    public void clickOnTopRatedNavigationItem_SelectTopRatedFragment() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(open()); // Open Drawer

        // Start top_rated screen.
        onView(withId(R.id.navigation_view))
                .perform(navigateTo(R.id.item_top_rated));

        onView(withId(R.id.viewpager))
                .check(matches(isDisplayed()));

        // Top_Rated fragment is in the 0th tab position which is checked if it is viewpager's current item.
        assertThat(mActivityTestRule.getActivity().getViewPager().getCurrentItem(), Matchers.equalTo(0));
    }

    @Test
    @UiThread
    public void checkTabSwitch() {
        String tabTitle = InstrumentationRegistry.getTargetContext()
                .getString(R.string.popular);
        // I'd like to switch to a tab popular and check that the switch happened
        onView(allOf(withText(tabTitle), isDescendantOfA(withId(R.id.tabs))))
                .perform(click())
                .check(matches(isDisplayed()));

        // Then I'd like to check that the current item in viewpager is 2nd tab position where Popular fragment is placed.
        assertThat(mActivityTestRule.getActivity().getViewPager().getCurrentItem(), Matchers.equalTo(2));
    }

    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
        // Check that left drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))); // Left Drawer should be closed.

        // Open Drawer
        String navigateUpDesc = mActivityTestRule.getActivity()
                .getString(R.string.app_name);
        onView(withContentDescription(navigateUpDesc)).perform(click());

        // Check if drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT))); // Left drawer is open open.
    }

    @Test
    public void clickOnMenuItemSettings() {
        openActionBarOverflowOrOptionsMenu(getTargetContext());
        Intents.init();
        onView(anyOf(withText(R.string.settings), withId(R.id.menu_settings))).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), SettingsActivity.class)));
        Intents.release();
    }

}
