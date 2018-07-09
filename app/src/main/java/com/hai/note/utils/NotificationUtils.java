package com.hai.note.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hai.note.R;
import com.hai.note.activity.EditNoteActivity;
import com.hai.note.activity.MainActivity;
import com.hai.note.model.Note;
import com.hai.note.receiver.NotificationPublisher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Hai on 09/07/2018.
 */

public class NotificationUtils {
    public static final String NOTE = "NOTE";
    public static void createNotification(Context mContext,Note note){
        Log.d("Notification","start");
        Intent intent = new Intent(mContext, NotificationPublisher.class);
        intent.putExtra(NOTE,note);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext.getApplicationContext(), note.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeAlarm ;
        try {
            timeAlarm = DateFormatUtils.getTimeMilisecond(note.getAlarmTime(),DateFormatUtils.DATE_TIME);
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeAlarm , pendingIntent);
        } catch (ParseException e) {

        }

    }
    public static void cancelNotification(Context mContext,Note note){
        Log.d("Notification","cancel");
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(mContext, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext,  note.getId(), myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }
    public static Notification getNotification(Note note,Context mContext) {
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentTitle(note.getTitle());
        builder.setContentText(note.getNote());
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(mContext, EditNoteActivity.class);
        List<Note> notes =new ArrayList<Note>();
        notes.add(note);
        intent.putParcelableArrayListExtra(MainActivity.LIST_NOTE, (ArrayList<? extends Parcelable>) notes);
        intent.putExtra(MainActivity.POSTITION,0);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, note.getId(),
               intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        return builder.build();
    }
}
