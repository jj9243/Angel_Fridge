package com.namjongbin.fridge_angel;

import android.content.Context;
import android.widget.ListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeFragmentTest {
    Context mMockContext;
    @Test
    public void addition_isCorrect(){
        HomeFragment r = new HomeFragment();
        assertEquals(0,r.countdday(2018,06,13));
        assertEquals(1,r.countdday(2018,06,14));
    }
}
