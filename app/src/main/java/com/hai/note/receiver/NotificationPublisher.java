package com.hai.note.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hai.note.model.Note;
import com.hai.note.utils.NotificationUtils;

/**
 * Created by Hai on 09/07/2018.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        Log.d("Notification","ok");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Note mNote = intent.getParcelableExtra(NotificationUtils.NOTE);
        Notification notification = NotificationUtils.getNotification(mNote,context);
        int id = intent.getIntExtra(NOTIFICATION_ID, mNote.getId());
        notificationManager.notify(id, notification);

    }
}