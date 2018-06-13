package com.namjongbin.fridge_angel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        
        AlarmBroadcast alarm = new AlarmBroadcast();
        alarm.parseItem("과자 2018년 7월 14일");
        assertEquals("과자",alarm.title);
        assertEquals(2018,alarm.year);
        assertEquals(7,alarm.month);
        assertEquals(14,alarm.day);

    }
}
