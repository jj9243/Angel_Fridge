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
public class ListAdapterTest {
    Context mMockContext;
    @Test
    public void addition_isCorrect(){
       RecognizerFragment r = new RecognizerFragment();
       r.parseVoice("과자 2018년 9월 17일");
       assertEquals("과자 ",r.item);
       assertEquals(2018,r.year);
       assertEquals(9,r.month);
       assertEquals(17,r.day);
    }
}
