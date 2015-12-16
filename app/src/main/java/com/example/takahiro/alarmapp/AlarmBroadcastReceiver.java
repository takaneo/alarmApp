package com.example.takahiro.alarmapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Takahiro on 2015/11/23.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // RecieverからMainActivityを起動させる処理ここから
//        Intent intent2 = new Intent(context, MainActivity.class);

        Intent intent2 = new Intent(context, SubActivity.class);


        // PendingIntentを使用 getActivityの第二引数について http://9ensan.com/blog/smartphone/android/android-pendingintent-putextra/
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent2, 0);
        //----------------ここまでがIntentの生成。実際にセットはされてないので発動はしない-----------------------

        // Notificationを管理するクラス
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)  //http://andante.in/i/android%E3%82%A2%E3%83%97%E3%83%AAtips/notification%E3%82%92%E4%BD%BF%E3%81%86%E3%80%82/
                .setSmallIcon(R.mipmap.ic_launcher) //http://ymmtmsys.hatenablog.com/entry/2015/06/25/231546
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("TestAlarm2")
                .setContentText("時間になりました")
                .setDefaults(Notification.DEFAULT_ALL) // 音、バイブレート、LEDで通知
                    // 通知をタップした時にPendingIntent(MainActivity)を立ち上げる設定が完了? http://mousouprogrammer.blogspot.jp/2013/05/android_13.html
                .setContentIntent(pendingIntent)
                .build();
        //----------------ここまでが新しい通知の設定。buildで実際に生成が完了されるものと思われる。ただし通知設定自体はnotificationManager.notifyでされる?-------

        // 古い通知を削除
        notificationManager.cancelAll();

        // 通知を設定する
        // 引数について http://tsukaayapontan.web.fc2.com/doc/notification/notification.html
        notificationManager.notify(R.string.app_name, notification); // R.string.app_nameでアプリ名のリソースIDが取得できるらしい。。int型

 /*       // toast で受け取りを確認
        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();
 */
    }
}
