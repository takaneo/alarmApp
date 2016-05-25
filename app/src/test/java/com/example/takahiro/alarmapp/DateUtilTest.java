package com.example.takahiro.alarmapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Takahiro on 2016/04/20.
 */
@RunWith(Theories.class)
public class DateUtilTest {
    @Rule
    public H2DatabaseServer server = new H2DatabaseServer();

    @Before
    public void setUp() throws Exception {}
    @After
    public void tearDown() throws Exception {}

    @DataPoints
    public static FixTure[] PARAMs = {
            new FixTure(2016,1,10,2016,1,10,true)  ,
            new FixTure(2016,1,10,2016,1,11,true)  ,
            new FixTure(2016,1,10,2016,1, 9,false) ,
            // テストケース増えるときはここに追記
    };

    @Theory
    public void is_Past(FixTure p) throws Exception{
        boolean actual = DateUtil.isPast(p.year, p.month, p.day, p.targetYear, p.targetMonth, p.targetDay);
        assertThat(actual, is(p.expected));
    }

    // パラメタテストで使用するクラス
    static  class FixTure {
        int year          ; int month        ; int day        ;
        int targetYear   ; int targetMonth ; int targetDay ;
        boolean expected ;

        FixTure(int year, int month, int day, int targetYear, int targetMonth, int targetDay, boolean expected){
            this.year = year;
            this.month = month;
            this.day = day;
            this.targetYear = targetYear;
            this.targetMonth = targetMonth;
            this.targetDay = targetDay;
            this.expected = expected;
        }
    }
}