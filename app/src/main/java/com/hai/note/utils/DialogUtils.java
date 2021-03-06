package com.hai.note.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Time;

import com.hai.note.R;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.hai.note.activity.AddNoteActivity.CAMERA_REQUEST;
import static com.hai.note.activity.AddNoteActivity.GALLERY_REQUEST;


/**
 * Created by Hai on 06/07/2018.
 */

public class DialogUtils {
    public static void showDatePicker(Context mContext, DatePickerDialog.OnDateSetListener datePickerListener) {
        Time currDate = new Time(Time.getCurrentTimezone());
        currDate.setToNow();
        DatePickerDialog d = new DatePickerDialog(mContext, datePickerListener,
                currDate.year, currDate.month, currDate.monthDay);
        d.show();
    }

    public static void showTimePicker(Context mContext, TimePickerDialog.OnTimeSetListener listener) {
        Time currDate = new Time(Time.getCurrentTimezone());
        currDate.setToNow();
        TimePickerDialog d = new TimePickerDialog(mContext, listener,
                currDate.hour, currDate.minute, true);
        d.show();
    }

    public static void showColorPicker(Activity activity, ColorPicker.OnChooseColorListener listener) {
        ColorPicker colorPicker = new ColorPicker(activity);
        int color[] = activity.getResources().getIntArray(R.array.color_picker);
        colorPicker.setColors(color);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(listener);
    }

    public static void showImagePicker(final Activity activity, final Uri file) {
        final CharSequence[] items = {activity.getString(R.string.camera), activity.getString(R.string.gallery)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.insert_image));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(items[0])) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                    activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (items[item].equals(items[1])) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    activity.startActivityForResult(intent, GALLERY_REQUEST);
                }
            }
        });
        builder.show();
    }

    public static void showDialogYesNo(Context mContext, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("YES", listener);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
