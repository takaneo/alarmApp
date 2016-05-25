package com.example.takahiro.alarmapp;

import com.facebook.stetho.server.ServerManager;

import org.h2.tools.Server;
import org.h2.util.JdbcUtils;
import org.junit.rules.ExternalResource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Takahiro on 2016/04/19-21
 */
public class H2DatabaseServer extends ExternalResource{

    private Server server = null;
    private Connection conn = null;
    private final String dbName = "test"; //DB名
    private final String schemaName = "adhd"; //スキーマ名

    @Override
    protected void before() throws Throwable {
        // DBサーバ起動
        server = Server.createTcpServer().start(); // インメモリで起動するので引数なし

        // ID, Pass設定
        Properties props = new Properties();
//        props.setProperty("user", "paparoach");
//        props.setProperty("password", "paparoach");
//        インメモリなのでID, Passは不要。。

        String url = "jdbc:h2:mem:" + dbName + "DB_CLOSE_DELAY=-1";// インメモリ(オプション付)で起動
        // 起動したDBサーバにコネクト
        conn = org.h2.Driver.load().connect(url, props);

        // インメモリDBの動作確認 (テストコードなのであとで消す)------------------------------------------------------
        Statement st1 = conn.createStatement();
        st1.execute("create table test (id int primary key, name varchar)");
        st1.execute("insert into test values (1, 'hoge')");
        ResultSet rs1 = st1.executeQuery("select * from test");
        if(rs1.next()){
            System.out.println("DB_インメモリ起動確認");
        }
        //------------------------------------------------------------------------------------------------------------
        try {
            conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
        } finally {
            JdbcUtils.closeSilently(conn);
        }
    }

    @Override
    protected void after() {
        //DBサーバ停止
        server.shutdown();
    }
}
