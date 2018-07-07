package com.hai.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hai.note.db.table.ImageNoteTable;
import com.hai.note.db.table.NoteTable;
import com.hai.note.model.ImageNote;
import com.hai.note.model.Note;

/**
 * Created by Hai on 06/07/2018.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NoteTable.CREATE_TABLE);
        db.execSQL(ImageNoteTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NoteTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImageNoteTable.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }



}
