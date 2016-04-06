package com.example.takahiro.alarmapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Takahiro on 2016/04/03.
 */
public class DateUtilTest {

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void dateCheckTest() {
        // 期待値
        boolean expected = false;

        //基準日を取得
        Calendar cal_1 = Calendar.getInstance();
        int year  = cal_1.get(Calendar.YEAR);
        int month = cal_1.get(Calendar.MONTH);
        int day   = cal_1.get(Calendar.DAY_OF_MONTH);

        // 基準日の1日前を取得
        cal_1.add(Calendar.DATE, -1);
        int pastYear  = cal_1.get(Calendar.YEAR);
        int pastMonth = cal_1.get(Calendar.MONTH);
        int pastDay   = cal_1.get(Calendar.DAY_OF_MONTH);

        // staticメソッドなのでインスタンス生成不要
        boolean actual = DateUtil.dateCheck(year, month, day, pastYear, pastMonth, pastDay);
        assertThat(actual, is(expected)); //CoreMatchersは手動でstaticインポート
    }

    @Test
    public void dateCheckTest_2() {
        // 期待値
        boolean expected = true;

        //基準日を取得
        Calendar cal_1 = Calendar.getInstance();
        int year  = cal_1.get(Calendar.YEAR);
        int month = cal_1.get(Calendar.MONTH);
        int day   = cal_1.get(Calendar.DAY_OF_MONTH);

        // 基準日の1日後を取得
        cal_1.add(Calendar.DATE, 1);
        int pastYear  = cal_1.get(Calendar.YEAR);
        int pastMonth = cal_1.get(Calendar.MONTH);
        int pastDay   = cal_1.get(Calendar.DAY_OF_MONTH);

        boolean actual = DateUtil.dateCheck(year, month, day, pastYear, pastMonth, pastDay);
        assertThat(actual, is(expected));
    }
}