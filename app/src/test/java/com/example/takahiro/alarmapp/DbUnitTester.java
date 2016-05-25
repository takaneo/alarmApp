package com.example.takahiro.alarmapp;


import android.util.EventLogTags;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.h2.Driver;
import org.h2.util.JdbcUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Takahiro on 2016/04/25.
 */
public abstract class DbUnitTester extends AbstractDatabaseTester implements TestRule{

    @Override
    public IDatabaseConnection getConnection() throws Exception{
        Driver.load();
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"); // DB_CLOSE_DELAYオプション付き
        DatabaseConnection dbConnection = new DatabaseConnection(conn);
        DatabaseConfig config = dbConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());

        // スキーマ作成
        try {
            conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + "test");
        } finally {                                                             //スキーマ名
            JdbcUtils.closeSilently(conn);
        }
        return  dbConnection;
    }

    protected void executeQuery(String sql) throws Exception{
        // インメモリDBの動作確認 (テストコードなのであとで消す)------------------------------------------------------
        Connection conn = getConnection().getConnection(); //getConn2回必要
        Statement st1 = conn.createStatement();
        st1.execute("create table test (id int primary key, name varchar)");
        st1.execute("insert into test values (1, 'hoge')");
        ResultSet rs1 = st1.executeQuery("select * from test");
        if(rs1.next()){
            System.out.println("DB_インメモリ起動確認");
        }
        st1.close();  // ステートメントをクローズ
        conn.close(); // コネクションをクローズ
        //------------------------------------------------------------------------------------------------------------
/*        //SQL実行メソッド
        Connection conn = getConnection().getConnection(); //2回いらないかも
        conn.createStatement().executeQuery(sql);
        conn.commit();
        conn.close();*/
    }

    protected void before() throws Exception{
    }

    protected void after() throws Exception{
    }

    abstract protected IDataSet createDataSet() throws Exception;

    // java.sql.Stetementと間違えない!
    @Override
    public org.junit.runners.model.Statement apply (final org.junit.runners.model.Statement base, Description Ddescription){
        return new org.junit.runners.model.Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                setDataSet(createDataSet());
                onSetup();
                try {
                    base.evaluate();
                } finally {
                    try {
                        after();
                    } finally {
                        onTearDown();
                    }
                }
            }
        };
    }
}
/*
AbstractDatabaseTseterを継承させようと思ったけど、予測変換にでず。
        原因は取り込んでないから。ネットからjarファイル取得＆libs配下に設置＆build.gradleに追記＆rsync
        警告でなければOK
*/
