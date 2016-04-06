package com.example.takahiro.alarmapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatePicker datepicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);

        // Stetho
        Stetho.initializeWithDefaults(this);

        // 現在日付取得
        Calendar cal = Calendar.getInstance();
        final int year  = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day   = cal.get(Calendar.DAY_OF_MONTH);

        // カレンダーに初期値設定
        datepicker = (DatePicker)findViewById(R.id.dp);
        datepicker.updateDate(year, month, day);

        // 選択できる年月日を制限
        // 最小値 http://stackoverflow.com/questions/13661788/how-to-set-minimum-datepicker-date-to-current-date
        datepicker.setMinDate(System.currentTimeMillis() - 1000);
        // 最大値
        cal.add(Calendar.YEAR, 1);
        datepicker.setMaxDate(cal.getTimeInMillis());

        // 次へボタン押下(入力された年月日を保存して、次の画面に遷移)
        Button btn = (Button)this.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // データチェック(引数:選択された年月日)
                // this.dateCheckだとメソッド呼び出せない
                boolean rtnFlag =  DateUtil.dateCheck(year, month, day,
                        datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());

                if (rtnFlag) {
                    Intent intent = new Intent();
                    intent.setClassName("com.example.takahiro.alarmapp", "com.example.takahiro.alarmapp.SubActivity");

                    // 入力された年月日をListに詰めて渡す
                    ArrayList<Integer> recArray = new ArrayList<>();
                    recArray.add(0, datepicker.getYear());
                    recArray.add(1, datepicker.getMonth());
                    recArray.add(2, datepicker.getDayOfMonth());
                    intent.putExtra("reserveData", recArray); // reserveDataという名称を付ける
                    startActivity(intent);
                } else {
                    // http://yuki312.blogspot.jp/2012/02/thisgetapplicationcontextactivityapplic.html  今回発生したエラーMSGに関する記述ある
                    // http://blog.fenrir-inc.com/jp/2012/03/android-beginner-code-style.html

                    // 【NG】 AlertDialog.Builder alertDlg = new AlertDialog.Builder( getApplicationContext());
                    // 【NG】 AlertDialog.Builder alertDlg = new AlertDialog.Builder( getApplication());
                    // 【NG】 AlertDialog.Builder alertDlg = new AlertDialog.Builder( this ); コンパイル通らない.

                    // //クラス名.thisを使用。ここはButtionクラスの中なのでただのthisだと、Buttonと認識されている??
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder( MainActivity.this );
                    alertDlg.setTitle("エラー");
                    alertDlg.setMessage("過去日は指定できません！");
                    alertDlg.setPositiveButton("OK", null); // クリック時のイベント不要なので、第二引数にnull指定
                    alertDlg.create().show();
                }
            }

/*            // 選択された年月日が現在日付より後か？
            // OK → true   NG → False
            // http://www.repica.jp/staffblog/tech/2012/08/17/633/
            private boolean dateCheck(int targetYear, int targetMonth, int targetDay) {
                boolean rtnFlag = false;
                Calendar nowDate = Calendar.getInstance();
                nowDate.set(year, month, day, 0, 00, 00);
                nowDate.set(Calendar.MILLISECOND, 0);

                Calendar targetDate = Calendar.getInstance();
                targetDate.set(targetYear, targetMonth, targetDay, 0, 00, 00);
                targetDate.set(Calendar.MILLISECOND,0);

                int diff = targetDate.compareTo(nowDate);
                if (diff == 0 || diff > 0) {
                    rtnFlag = true;
                }
                return rtnFlag;
            }*/
        });

        // 履歴ボタン
        Button btnHist = (Button)this.findViewById(R.id.btnHist);
        btnHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.takahiro.alarmapp", "com.example.takahiro.alarmapp.HistActivity");
                startActivity(intent);
            }
        });
    }
}
