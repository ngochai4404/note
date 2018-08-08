package com.hai.note.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai on 06/07/2018.
 */

public class Note implements Parcelable {

    private int id;
    private boolean alarm = false;
    private String title;
    private String note;
    private String noteTime;
    private String alarmTime;
    private int color;
    private List<ImageNote> imgs;

    public Note() {
        imgs = new ArrayList<>();
    }

    protected Note(Parcel in) {
        id = in.readInt();
        alarm = in.readByte() != 0;
        title = in.readString();
        note = in.readString();
        noteTime = in.readString();
        alarmTime = in.readString();
        color = in.readInt();
        imgs = in.createTypedArrayList(ImageNote.CREATOR);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<ImageNote> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImageNote> imgs) {
        this.imgs = imgs;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeByte((byte) (alarm ? 1 : 0));
        parcel.writeString(title);
        parcel.writeString(note);
        parcel.writeString(noteTime);
        parcel.writeString(alarmTime);
        parcel.writeInt(color);
        parcel.writeTypedList(imgs);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
