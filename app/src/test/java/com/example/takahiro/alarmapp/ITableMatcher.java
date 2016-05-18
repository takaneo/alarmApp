package com.example.takahiro.alarmapp;

import org.dbunit.DatabaseUnitException;
import org.dbunit.DatabaseUnitRuntimeException;
import org.dbunit.dataset.ITable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Takahiro on 2016/05/15.
 * このクラスについて → DBUnitもJunit4スタイルに統一したので、MatcherもJuni4スタイルとするため作成
 * Junit4スタイルのMatcher(第一引数が実測値、第二引数が期待値)。Staticインポートで利用すること
 * このクラスがないとJuni3のMatcherを利用することになるが、その場合、第一引数が期待値、第二引数が実測値
 */
public class ITableMatcher extends TypeSafeMatcher<ITable>{

    public static Matcher<ITable> tableOf(ITable expected) {
        return new ITableMatcher(expected);
    }

    // メンバ
    private final ITable expected;
    String assertionMsg = null;

    // 引数付コンストラクタ
    ITableMatcher(ITable expected){
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(ITable actual) {
        try {
            org.dbunit.Assertion.assertEquals(expected, actual);
        } catch (DatabaseUnitException e){
            throw new DatabaseUnitRuntimeException(e);
        } catch (AssertionError e){
            assertionMsg = e.getMessage();
        }
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
        if (assertionMsg == null){
            return;
        }
        desc.appendText("\n >>>").appendText(assertionMsg);
    }
}
