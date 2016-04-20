package com.example.takahiro.alarmapp;

import com.facebook.stetho.server.ServerManager;

import org.h2.tools.Server;
import org.h2.util.JdbcUtils;
import org.junit.rules.ExternalResource;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Takahiro on 2016/04/19-21
 */
public class H2DatabaseServer extends ExternalResource{

    private final String baseDir;
    private final String dbName;
    private final String schemaName;
    private Server server = null;

    public H2DatabaseServer(String baseDir, String dbName, String schemaName){
        this.baseDir = baseDir;
        this.dbName = dbName;
        this.schemaName = schemaName;
    }

    @Override
    protected void before() throws Throwable {
        // DBサーバ起動
        server = Server.createPgServer("-baseDir", baseDir);
        server.start();
        // スキーマ設定
        Properties props = new Properties();
        props.setProperty("user", "paparoach");
        props.setProperty("password", "paparoach");
//        String url = "jdbc:h2:" + server.getURL() + "/" + dbName;
        // 直接指定
        String url = "jdbc:h2:C:\\Users\\Takahiro\\Documents\\h2dataBaseTest/" + dbName;
        Connection conn = org.h2.Driver.load().connect(url, props);
        try {
            conn.createStatement()
                    .execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
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
