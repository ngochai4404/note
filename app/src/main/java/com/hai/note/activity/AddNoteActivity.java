package com.hai.note.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hai.note.R;
import com.hai.note.activity.base.BaseActivity;
import com.hai.note.custom.adapter.ImageAdapter;
import com.hai.note.db.DatabaseManager;
import com.hai.note.db.table.NoteTable;
import com.hai.note.model.ImageNote;
import com.hai.note.model.Note;
import com.hai.note.utils.DateFormatUtils;
import com.hai.note.utils.DialogUtils;
import com.hai.note.utils.FileUtils;
import com.hai.note.utils.NotificationUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class AddNoteActivity extends BaseActivity {
    private static final int PERMISSION_CODE = 99;
    public static final int GALLERY_REQUEST = 100;
    public static final int CAMERA_REQUEST = 101;
    String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.tv_date_alarm)
    TextView tvDateAlarm;
    @BindView(R.id.tv_time_alarm)
    TextView tvTimeAlarm;
    @BindView(R.id.tv_date)
    TextView tvDateNote;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.sv_root)
    ScrollView svRoot;
    @BindView(R.id.rcv_img)
    RecyclerView rcvImage;

    Uri mFile = null;
    ImageAdapter mAdapter;
    Date mDateNote, mDateAlarm;
    Note mNote;
    ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        bindingData();
    }

    private void bindingData() {
        mNote = new Note();
        mDateNote = new Date();
        tvDateNote.setText(DateFormatUtils.dateFormat(mDateNote, DateFormatUtils.DATE_TIME));
        mImageAdapter = new ImageAdapter(this, mNote.getImgs());
        rcvImage.setAdapter(mImageAdapter);
        rcvImage.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @OnClick({R.id.tv_date_alarm, R.id.ibtn_date_alarm})
    public void showDatePicker() {
        DialogUtils.showDatePicker(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (mDateAlarm == null) {
                    mDateAlarm = new Date();
                    tvTimeAlarm.setText(DateFormatUtils.dateFormat(mDateAlarm, DateFormatUtils.TIME));
                }
                mDateAlarm.setYear(i - 1900);
                mDateAlarm.setMonth(i1);
                mDateAlarm.setDate(i2);
                tvDateAlarm.setText(DateFormatUtils.dateFormat(mDateAlarm, DateFormatUtils.DATE));
            }
        });
    }

    @OnClick({R.id.tv_time_alarm, R.id.ibtn_time_alarm})
    public void showTimePicker() {
        DialogUtils.showTimePicker(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (mDateAlarm == null) {
                    mDateAlarm = new Date();
                    tvDateAlarm.setText(DateFormatUtils.dateFormat(mDateAlarm, DateFormatUtils.DATE));
                }
                mDateAlarm.setHours(i);
                mDateAlarm.setMinutes(i1);
                tvTimeAlarm.setText(DateFormatUtils.dateFormat(mDateAlarm, DateFormatUtils.TIME));
            }
        });
    }

    @OnClick(R.id.ibtn_delete)
    public void deleteAlarm() {
        mDateAlarm = null;
        tvDateAlarm.setText(DateFormatUtils.DATE);
        tvTimeAlarm.setText(DateFormatUtils.TIME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_note, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    public Uri createFile() {
        if (mFile == null) {
            mFile = Uri.fromFile(FileUtils.getOutputMediaFile());
        }
        return mFile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_choose_color:
                DialogUtils.showColorPicker(this, new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        svRoot.setBackgroundColor(color);
                        mNote.setColor(color);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                break;
            case R.id.action_insert_image:
                if (hasPermissions(this, permissions)) {
                    DialogUtils.showImagePicker(this, createFile());
                } else {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
                }
                break;
            case R.id.action_done:
                saveNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNote() {
        mNote.setTitle(etTitle.getText().toString());
        mNote.setNote(etNote.getText().toString());
        if (mDateAlarm == null) {
            mNote.setAlarm(false);
        } else {
            mNote.setAlarm(true);
            mNote.setAlarmTime(DateFormatUtils.dateFormat(mDateAlarm, DateFormatUtils.DATE_TIME));
        }
        mNote.setNoteTime(DateFormatUtils.dateFormat(mDateNote, DateFormatUtils.DATE_TIME));
        DatabaseManager db = new DatabaseManager(this);
        NoteTable noteTable = new NoteTable();
        int id = noteTable.insertNote(mNote, db);
        if (mNote.isAlarm()) {
            NotificationUtils.createNotification(getBaseContext(), mNote);
        }
        finish();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            String path = FileUtils.FILE + mFile.getPath();
            mNote.getImgs().add(new ImageNote(path));
            mImageAdapter.notifyDataSetChanged();
            mFile = null;
        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            String path = data.getData().toString();
            mNote.getImgs().add(new ImageNote(path));
            mImageAdapter.notifyDataSetChanged();
        }
    }
}
