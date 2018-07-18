package com.example.hsn07.calarsaat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Hsn07 on 5.6.2017.
 */

public class Alarmreciver extends BroadcastReceiver {
    int zaman=3;
    NotificationManager notificationManager;
    SharedPreferences sharedPreferences;
    String dosyaadi="com.example.hsn07.calarsaat";
    Boolean durum;
    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences=context.getSharedPreferences(dosyaadi,context.MODE_PRIVATE);
        durum=sharedPreferences.getBoolean("sbildirim",false);
        if (durum){
            bildirim(context);
        }else{

            Toast.makeText(context, "Alarm Çalıyor! Artık Uyan!", Toast.LENGTH_LONG).show();
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final MediaPlayer mediaPlayer = MediaPlayer.create(context,alarmUri);
            //final Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            Timer timer = new Timer();
            if (alarmUri == null)
            {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Intent i = new Intent(context,Anasayfa.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            Calendar c=Calendar.getInstance();
            ///////////////////////////////////////////////////////////////////////////
            String get = sharedPreferences.getString("notification_text","");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("notification_text",get+","+ c.getTime().getHours()+":"+c.getTime().getMinutes());
            editor.commit();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (zaman==0)
                        mediaPlayer.stop();
                    else
                        zaman--;
                }
            },1,1000);
            //ringtone.play();
            mediaPlayer.start();
        }

        //////////////////////////////////////////////////////////////////////////////
        SharedPreferences.Editor editor1=sharedPreferences.edit();
        editor1.putBoolean("alarmdurum",true);
        editor1.putInt("alarmid",1);
        editor1.putString("alarmnot","ALARM");
        editor1.commit();
        //////////////////////////////////////////////////////////////////////////////
    }
    public void bildirim(Context context){
        final Notification.Builder builder=new Notification.Builder(context);
        notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        builder.setContentTitle("ALARM !!!");
        builder.setContentText("Merhaba");
        builder.setSmallIcon(R.drawable.saat);
        builder.setAutoCancel(true);
        builder.setTicker("Alarm çalıyor...");
        builder.setVibrate(new long[]{100,200,300,400});
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intentbildirim=new Intent(context,Anasayfa.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intentbildirim,0);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.getNotification();
        notificationManager.notify(1,notification);

    }

}
