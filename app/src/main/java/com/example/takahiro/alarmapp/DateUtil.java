package com.example.takahiro.alarmapp;

import java.util.Calendar;

/**
 * Created by Takahiro on 2016/04/03.
 */
public class DateUtil {

    /**
     * 日付比較を行う。年・月・日のみ
     * Targetが基準日と同じか過去日の場合True
     * @param year  (基準日・年)
     * @param month (基準日・月)
     * @param day   (基準日・日)
     * @param targetYear    (Traget・年)
     * @param targetMonth   (Traget・月)
     * @param targetDay     (Traget・日)
     * @return True:OK Falase:NG
     * 参考URL http://www.repica.jp/staffblog/tech/2012/08/17/633/
     */
    public static boolean dateCheck(int year, int month, int day,
                             int targetYear, int targetMonth, int targetDay) {
        boolean rtnFlag = false;
        Calendar date = Calendar.getInstance();
        date.set(year, month, day, 0, 00, 00);
        date.set(Calendar.MILLISECOND, 0);

        Calendar targetDate = Calendar.getInstance();
        targetDate.set(targetYear, targetMonth, targetDay, 0, 00, 00);
        targetDate.set(Calendar.MILLISECOND,0);

        int diff = targetDate.compareTo(date);
        if (diff == 0 || diff > 0) {
            rtnFlag = true;
        }
        return rtnFlag;
    }
}
