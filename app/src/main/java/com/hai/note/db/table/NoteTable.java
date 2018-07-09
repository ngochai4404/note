package com.hai.note.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hai.note.db.DatabaseManager;
import com.hai.note.model.ImageNote;
import com.hai.note.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai on 06/07/2018.
 */

public class NoteTable {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TIME_ALARM = "time_alarm";
    public static final String COLUMN_TIME_NOTE = "time_note";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_ALARM = "alarm";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOTE + " TEXT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_COLOR + " INTEGER,"
            + COLUMN_TIME_ALARM + " TEXT,"
            + COLUMN_TIME_NOTE + " TEXT,"
            + COLUMN_ALARM + " INTEGER DEFAULT 0"
            + ")";
    public int insertNote(Note note, DatabaseManager data) {
        SQLiteDatabase db = data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_TITLE,note.getTitle());
        values.put(NoteTable.COLUMN_NOTE, note.getNote());
        values.put(NoteTable.COLUMN_COLOR, note.getColor());
        values.put(NoteTable.COLUMN_TIME_ALARM, note.getAlarmTime());
        values.put(NoteTable.COLUMN_TIME_NOTE, note.getNoteTime());
        values.put(NoteTable.COLUMN_ALARM,note.isAlarm());
        long id = db.insert(NoteTable.TABLE_NAME, null, values);
        db.close();
        note.setId((int) id);
        ImageNoteTable table = new ImageNoteTable();
        table.insertImage(note,data);
        return (int) id;
    }
    public List<Note> getAllNotes(DatabaseManager databaseManager) {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NoteTable.TABLE_NAME ;

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        ImageNoteTable imageNoteTable = new ImageNoteTable();
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(NoteTable.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_NOTE)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TITLE)));
                note.setAlarmTime(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TIME_ALARM)));
                note.setNoteTime(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TIME_NOTE)));
                note.setAlarm(cursor.getInt(cursor.getColumnIndex(NoteTable.COLUMN_ALARM))==1?true:false);
                note.setColor(cursor.getInt(cursor.getColumnIndex(NoteTable.COLUMN_COLOR)));
                note.setImgs((ArrayList<ImageNote>) imageNoteTable.getAllImageNotes(databaseManager,note.getId()));
                Log.d("noteItem",note.getImgs().size()+"");
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;
    }
    public void deleteNote(Note note,DatabaseManager databaseManager) {
        new ImageNoteTable().deleteImageNote(note,databaseManager);
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public void updateNote(Note note,DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_TITLE,note.getTitle());
        values.put(NoteTable.COLUMN_NOTE, note.getNote());
        values.put(NoteTable.COLUMN_COLOR, note.getColor());
        values.put(NoteTable.COLUMN_TIME_ALARM, note.getAlarmTime());
        values.put(NoteTable.COLUMN_TIME_NOTE, note.getNoteTime());
        values.put(NoteTable.COLUMN_ALARM,note.isAlarm());

        // updating row
        int id =  db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        new ImageNoteTable().deleteImageNote(note,databaseManager);
        new ImageNoteTable().insertImage(note,databaseManager);
    }
}
