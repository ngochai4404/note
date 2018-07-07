package com.hai.note.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hai.note.db.DatabaseManager;
import com.hai.note.model.ImageNote;
import com.hai.note.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai on 06/07/2018.
 */

public class ImageNoteTable  {
    public static final String TABLE_NAME = "note_image";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_NOTE_ID= "idNote";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IMAGE + " TEXT,"
            + COLUMN_NOTE_ID + " INTEGER"
            + ")";
    public void insertImage(Note note, DatabaseManager data) {
        SQLiteDatabase db = data.getWritableDatabase();
        for (ImageNote img: note.getImgs()){
            ContentValues values = new ContentValues();
            values.put(ImageNoteTable.COLUMN_NOTE_ID, note.getId());
            values.put(ImageNoteTable.COLUMN_IMAGE, img.getPath());
            long id = db.insert(NoteTable.TABLE_NAME, null, values);
        }
        db.close();
    }
    public List<ImageNote> getAllImageNotes(DatabaseManager databaseManager, int idNote) {
        List<ImageNote> imgs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+COLUMN_NOTE_ID +" = "+idNote;

        SQLiteDatabase db = databaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImageNote img = new ImageNote();
                img.setId(cursor.getInt(cursor.getColumnIndex(ImageNoteTable.COLUMN_ID)));
                img.setPath(cursor.getString(cursor.getColumnIndex(ImageNoteTable.COLUMN_IMAGE)));

                imgs.add(img);
            } while (cursor.moveToNext());
        }

        db.close();
        return imgs;
    }
}
