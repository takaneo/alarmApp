package com.example.takahiro.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Takahiro on 2015/12/06.
 */
public class SubActivity extends AppCompatActivity {

    static SQLiteDatabase mydb;
    ArrayList<Integer> recArray = new ArrayList<>();
    TimePicker picker;

    // 画面が生成されるときの処理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        if(intent != null){//前の画面から遷移してきたかどうか
            // 現在の時刻取得
            Calendar calendar = Calendar.getInstance();
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // ダイアログ生成、及び初期値の設定
            picker = (TimePicker)findViewById(R.id.tp);
            //24時間表記に変更(setメソッド使用する前にtrueを設定しないとダメ!)
            picker.setIs24HourView(true);
            // level23以前は下記を使用
            picker.setCurrentHour(hourOfDay);
            picker.setCurrentMinute(minute);
            //  level23以降
            //  picker.setHour(hourOfDay);
            //  picker.setMinute(minute);
            // 前の画面から保持してきた年月日を保存
            recArray = intent.getIntegerArrayListExtra("reserveData");
        }

        // 予約ボタン押下時
        Button btn = (Button)this.findViewById(R.id.reserve);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                PendingIntent pending = PendingIntent.getBroadcast
                        (getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //http://www.atmarkit.co.jp/ait/articles/1202/16/news130_2.html

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, recArray.get(0));
                calendar.set(Calendar.MONTH, recArray.get(1));
                calendar.set(Calendar.DATE, recArray.get(2));

                // 指定された時間を直接設定
                calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
                calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // DBに予約情報を登録
                MySQLiteOpenHelper hlpr = new MySQLiteOpenHelper(getApplicationContext());
                mydb = hlpr.getWritableDatabase();
                SimpleDateFormat sdfForExecute  = new SimpleDateFormat();
                SimpleDateFormat sdfForRegister = new SimpleDateFormat();
                ContentValues values = new ContentValues();
                //http://java-reference.sakuraweb.com/java_date_format.html
                values.put("RegisterDate", sdfForRegister.format(calendar.getTime()));
                values.put("ExecuteDate" , sdfForExecute.format(calendar.getTime()));
                mydb.insert("alarmTalbe", null, values);

                /* 検索の処理(queryを使用) */
                // query(table名,field名(String[]),検索語,検索語に?がある場合に使用,GROUP BY，HAVING，ORDERBY)
                Cursor mCursor = mydb.query("alarmTalbe",
                        new String[] { "RegisterId", "RegisterDate", "ExecuteDate"},
                        null, null, null, null, null);

                // Cursorを先頭に移動する 検索結果が0件の場合にfalse
                if (mCursor.moveToFirst()) {
                    String dispId = mCursor.getString(mCursor
                            .getColumnIndex("RegisterId"));
                }

                // テストコード。DBに格納されてるデータ件数を確認
                Cursor smpCursor = mydb.rawQuery("SELECT count(*) as cnt FROM alarmTalbe", new String[]{});
                if (smpCursor.moveToFirst()) {
                    int tmpCnt = smpCursor.getInt(smpCursor.getColumnIndex("cnt"));
                    int tmp = 0;
                    int tmp1 = 1;
                }

                // アラームをセット
                Context context = SubActivity.this;
                AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

                Toast.makeText(
                        com.example.takahiro.alarmapp.SubActivity.this,
                        recArray.get(0).toString() + "年:" +
                                String.valueOf(recArray.get(1) +1) + "月:" +
                                recArray.get(2).toString() + "日:" +
                                picker.getCurrentHour().toString() + "時:" +
                                picker.getCurrentMinute().toString() + "分:" +
                                "予約完了しました！",
                        Toast.LENGTH_LONG).show();
            }
        });

        // 戻るボタン押下時
        Button back_btn = (Button)this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intentで戻るのではなく、このSubActivity自体を終了させることによって戻る
                // http://ichitcltk.hustle.ne.jp/gudon2/index.php?pageType=file&id=Android023_ActivityChange
                finish();
/*                Intent intent = new Intent();
                intent.setClassName("com.example.takahiro.alarmapp", "com.example.takahiro.alarmapp.MainActivity");
                startActivity(intent);*/
            }
        });

    }
}
