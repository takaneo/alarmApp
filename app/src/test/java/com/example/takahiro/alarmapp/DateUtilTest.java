package com.example.takahiro.alarmapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Takahiro on 2016/04/09.
 */
@RunWith(Enclosed.class)
public class DateUtilTest {

    @RunWith(Theories.class)
    public  static class isPast {
        @Before
        public void setUp() throws Exception {}
        @After
        public void tearDown() throws Exception {}

        @DataPoints
        public static FixTure[] PARAMs = {
                new FixTure()



        }


        // パラメータテストで使用するクラス
        static  class FixTure {
            int year ;
            int month ;
            int day ;
            int targetYear ;
            int targetMonth ;
            int targetDay ;
            boolean expected ;



            // 基準日が現在日付のとき使用するコンストラクタ
            FixTure(int addDay, boolean expected){
                // 現在日付
                Calendar cal_1 = Calendar.getInstance();
                this.year  = cal_1.get(Calendar.YEAR);
                this.month = cal_1.get(Calendar.MONTH);
                this.day   = cal_1.get(Calendar.DAY_OF_MONTH);

                // 引数で指定された日付
                cal_1.add(Calendar.DATE, addDay);
                this.targetYear  = cal_1.get(Calendar.YEAR);
                this.targetMonth = cal_1.get(Calendar.MONTH);
                this.targetDay   = cal_1.get(Calendar.DAY_OF_MONTH);

                this.expected = expected;
            }






            // 基準日が現在日付のとき使用するコンストラクタ
            FixTure(int targetYear, int targetMonth, int targetDay, boolean expected){
                Calendar cal_1 = Calendar.getInstance();
                this.year  = cal_1.get(Calendar.YEAR);
                this.month = cal_1.get(Calendar.MONTH);
                this.day   = cal_1.get(Calendar.DAY_OF_MONTH);
                this.targetYear = targetYear;
                this.targetMonth = targetMonth;
                this.targetDay = targetDay;
                this.expected = expected;
            }

            // 基準日が現在日付のとき以外に使用するコンストラクタ
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
}



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
        boolean actual = DateUtil.isPast(year, month, day, pastYear, pastMonth, pastDay);
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

        boolean actual = DateUtil.isPast(year, month, day, pastYear, pastMonth, pastDay);
        assertThat(actual, is(expected));
    }
}