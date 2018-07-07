package com.hai.note.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai on 06/07/2018.
 */

public class Note {

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
}
