package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by USER on 2018-06-13.
 */

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> listActivityRule = new ActivityTestRule<ListActivity>(ListActivity.class);

    @Test
    public void onCreate() throws Exception {
        Espresso.onView(withId(R.id.deleteBtn)).perform(click());
        //Espresso.onView(withId(R.id.f)).perform(click());

    }
}