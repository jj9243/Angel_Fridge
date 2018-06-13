package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by USER on 2018-06-13.
 */

@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {

    @Rule
    public ActivityTestRule<CategoryActivity> cActivityRule = new ActivityTestRule<CategoryActivity>(CategoryActivity.class);

    @Test
    public void onCreate() throws Exception {
        Espresso.onView(withId(R.id.child_item_icon)).perform(click());
        //Espresso.onView(withId(R.id.f)).perform(click());

    }
}