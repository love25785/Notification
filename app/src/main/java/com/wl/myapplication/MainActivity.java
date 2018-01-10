package com.wl.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    String idLove="love";
    final int NOTIFICATION_ID=123123;
    NotificationChannel channelLove;
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=26)//4 超過26版 才註冊CHANNEL
        {
            regChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O) //5 這是告知編譯器  這段用26版本去跑
    public void regChannel()  //6
    {
        channelLove=new NotificationChannel(idLove,"Channel Love",NotificationManager.IMPORTANCE_HIGH);
        channelLove.setDescription("最重要的人");
        channelLove.enableLights(true);
        channelLove.enableVibration(true);
        ///以上為創立一個NotificationChannel ,是在26版以後需要通知功能，要NotificationChannel才能用，所以要將gradle裡將minSdkVersion版本設成26
        /// 但目前市面上手機大部分都26以下，我們用26以下去跑的話，本來是會出錯，系統其實也會跳出語法過期的訊息，所以我們用if 和 else 和  @TargetApi   @SuppressWarnings 告知編譯器，這段式26在跑的
        nm.createNotificationChannel(channelLove);
    }


    @TargetApi(Build.VERSION_CODES.O)  //1 這是告知編譯器  這段用26版本去跑
    @SuppressWarnings("deprecation") //2 這是告知編譯器  即使語法過期了也沒關係
    public  void click1(View v)
    {
        Notification.Builder builder;
        if(Build.VERSION.SDK_INT>=26) //3這是在選擇跑不同版本的android 選擇不同的Notification.Builder
        {
            builder=new Notification.Builder(MainActivity.this,idLove); //這段語法是在26以上才能用
        }
        else
        {
            builder=new Notification.Builder(MainActivity.this); //25以下都是用這段
        }

        Intent it = new Intent(MainActivity.this, InfoActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 123, it, PendingIntent.FLAG_UPDATE_CURRENT);
        //以上三行 及下面builder.setContentIntent(pi);  是要讓人點通知 會跳到該activity

        builder.setContentTitle("測試");
        builder.setContentText("這是內容");
        if (Build.VERSION.SDK_INT >= 26)
        {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else
        {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        //↑如是跑26版以下將R.drawable.ic_launcher_foreground改為R.mipmap.ic_launcher
        // 因為26版以下還沒有R.drawable.ic_launcher_foreground這個xml圖示
        builder.setAutoCancel(true);//這是當使用者點通知後，該通知就會消掉
        builder.setContentIntent(pi); /////////
        Notification notify=builder.build();

        nm.notify(NOTIFICATION_ID,notify);
    }

        public void click2(View v)
        {
            nm.cancel(NOTIFICATION_ID);
        }
}

