package com.example.takahiro.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 日時を指定したアラーム
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
//                intent.putExtra("intentId", 2);
//                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0); //http://www.shaga-workshop.net/diary/20150521.html

                // アラームをセット
                AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
//                Toast.makeText(getApplicationContext(), "ALARM 2", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
