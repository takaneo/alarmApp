package com.example.takahiro.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatePicker datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Stetho
        Stetho.initializeWithDefaults(this);

        // 現在日付取得
        Calendar cal = Calendar.getInstance();
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day   = cal.get(Calendar.DAY_OF_MONTH);

        // カレンダーに初期値設定
        datepicker = (DatePicker)findViewById(R.id.dp);
        datepicker.updateDate(year, month, day);

        // 次へボタン押下(入力された年月日を保存して、次の画面に遷移)
        Button btn = (Button)this.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.takahiro.alarmapp", "com.example.takahiro.alarmapp.SubActivity");

                // 入力された年月日を保存 hashMapは無理そうなのでlist
                ArrayList<Integer> recArray = new ArrayList<>();
                recArray.add(0, datepicker.getYear());
//                recArray.add(1, datepicker.getMonth()+1);  +1してしまうとsubActivityで登録する時に+1ケ月してしまう！
                recArray.add(1, datepicker.getMonth());
                recArray.add(2, datepicker.getDayOfMonth());
                intent.putExtra("reserveData", recArray);
                startActivity(intent);
            }
        });




/*        // 日時を指定したアラーム
        Button btn3 = (Button)this.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2015);
                calendar.set(Calendar.MONTH, 10);// 10=>11月
                calendar.set(Calendar.DATE, 24);
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 22);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                //11月24日0時10分

                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                PendingIntent pending = PendingIntent.getBroadcast
                        (getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //http://www.atmarkit.co.jp/ait/articles/1202/16/news130_2.html
                //http://d.hatena.ne.jp/rso/20110302

                // アラームをセット
                AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
            }
        });*/

    }
}
