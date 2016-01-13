package com.example.takahiro.alarmapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Takahiro on 2016/01/11.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME   = "alarm.db";   // DB名
    static final int DB_VERSION  = 1;              // DBのVersion
    static final String CREATE_TABLE
            =  "create table alarmTalbe ( RegisterId integer primary key autoincrement, RegisterDate text not null, ExecuteDate text not null );";
    static final String DROP_TABLE = "drop table alarmTalbe;";

    // コンストラクタ
    public MySQLiteOpenHelper(Context mContext){
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    // DBが存在しない状態でOpenすると、onCreateがコールされる
    // 新規作成されたDBのインスタンスが付与されるので、テーブルを作成する。
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    // コンストラクタで指定したバージョンと、参照先のDBのバージョンに差異があるときにコールされる
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }
}