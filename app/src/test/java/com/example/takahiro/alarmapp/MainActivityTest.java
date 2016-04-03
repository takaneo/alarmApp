package com.example.takahiro.alarmapp;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers. *;

/**
 * Created by Takahiro
 */
public class MainActivityTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void dateCheckTest() {
        MainActivity act = new MainActivity();
        boolean expected = false;

        //実行日を取得
        Calendar cal_1 = Calendar.getInstance();
        int year  = cal_1.get(Calendar.YEAR);
        int month = cal_1.get(Calendar.MONTH);
        int day   = cal_1.get(Calendar.DAY_OF_MONTH);

        // 実行日の1日前を取得
        cal_1.add(Calendar.DATE, -1);
        int pastYear  = cal_1.get(Calendar.YEAR);
        int pastMonth = cal_1.get(Calendar.MONTH);
        int pastDay   = cal_1.get(Calendar.DAY_OF_MONTH);

        boolean actual = act.dateCheck(year, month, day, pastYear, pastMonth, pastDay);
        assertThat(actual, is(expected)); //CoreMatchersは手動でstaticインポート
    }

    @Test
    public void dateCheckTest_2() {
        MainActivity act = new MainActivity();
        boolean expected = true;

        //実行日を取得
        Calendar cal_1 = Calendar.getInstance();
        int year  = cal_1.get(Calendar.YEAR);
        int month = cal_1.get(Calendar.MONTH);
        int day   = cal_1.get(Calendar.DAY_OF_MONTH);

        // 実行日の1日後を取得
        cal_1.add(Calendar.DATE, 1);
        int pastYear  = cal_1.get(Calendar.YEAR);
        int pastMonth = cal_1.get(Calendar.MONTH);
        int pastDay   = cal_1.get(Calendar.DAY_OF_MONTH);

        boolean actual = act.dateCheck(year, month, day, pastYear, pastMonth, pastDay);
        assertThat(actual, is(expected));
    }
}