package com.example.takahiro.alarmapp;


import android.util.EventLogTags;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Takahiro on 2016/04/25.
 */
public abstract class DbUnitTester extends AbstractDatabaseTester implements TestRule{
    //このクラスはabstractクラス
    //Dbunitのテストを実行するためAbstractDatabaseTester継承
    //Junit4のフォーマットを適応するためTestRuleインタフェース実装

    private final String connectionUrl;
    private final String username;
    private final String password;

    // 引数付コンストラクタ_1
    public DbUnitTester(String driverClass, String connectionUrl){
        this(driverClass, connectionUrl, null, null);
    }
    // 引数付コンストラクタ_2
    public DbUnitTester(String driverClass, String connectionUrl, String username, String password){
        this(driverClass, connectionUrl, username, password, null);
    }
    // 引数付コンストラクタ_3
    public DbUnitTester(String driverClass, String connectionUrl, String username, String password, String schema){
        super(schema);
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        assertNotNullNorEmpty("driverClass", driverClass);
        try{
            //JDBCドライバロード
            Class.forName(driverClass);
        } catch (ClassNotFoundException e){
            throw  new AssertionError();
        }
    }
    
    @Override
    public IDatabaseConnection getConnection() throws Exception{
        // コネクション取得
        Connection conn = null;
        if(username == null && password == null){
            conn = DriverManager.getConnection(connectionUrl);
        }else{
            conn = DriverManager.getConnection(connectionUrl, username, password);
        }
        DatabaseConnection dbConnection = new DatabaseConnection(conn, getSchema());
        DatabaseConfig config = dbConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        return  dbConnection;
    }

    protected void executeQuery(String sql) throws Exception{
        //SQL実行メソッド
        Connection conn = getConnection().getConnection(); //2回いらないかも
        conn.createStatement().executeQuery(sql);
        conn.commit();
        conn.close();
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
