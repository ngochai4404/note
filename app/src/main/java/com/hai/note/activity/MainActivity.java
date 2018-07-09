package com.hai.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hai.note.R;
import com.hai.note.activity.base.BaseActivity;
import com.hai.note.custom.adapter.NoteAdapter;
import com.hai.note.db.DatabaseManager;
import com.hai.note.db.table.NoteTable;
import com.hai.note.interfaces.ItemOnClick;
import com.hai.note.model.ImageNote;
import com.hai.note.model.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static final String LIST_NOTE = "LIST_NOTE";
    public static final String POSTITION = "POSTITION";
    @BindView(R.id.rcv_note) RecyclerView rcvNote;
    List<Note> mNotes;
    NoteAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mNotes = new ArrayList<>();
        mAdapter = new NoteAdapter(mNotes, new ItemOnClick() {
            @Override
            public void onClick(Object object, int pos) {
                Intent t = new Intent(getBaseContext(),EditNoteActivity.class);
                t.putExtra(POSTITION,pos);
                t.putParcelableArrayListExtra(LIST_NOTE, (ArrayList<? extends Parcelable>) mNotes);
                startActivity(t);
            }
        });
        rcvNote.setLayoutManager(new GridLayoutManager(this,2));
        rcvNote.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_note:
                startActivity(new Intent(this,AddNoteActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
    private void initView(){
        DatabaseManager db = new DatabaseManager(this);
        mNotes.clear();
        mNotes.addAll(new NoteTable().getAllNotes(db));
        mAdapter.notifyDataSetChanged();
    }
}
