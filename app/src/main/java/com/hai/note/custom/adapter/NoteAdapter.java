package com.hai.note.custom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hai.note.R;
import com.hai.note.interfaces.ItemOnClick;
import com.hai.note.model.Note;
import com.hai.note.utils.DateFormatUtils;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hai on 09/07/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    List<Note> mNotes;
    ItemOnClick itemOnClick;

    public NoteAdapter(List<Note> mNotes, ItemOnClick itemOnClick) {
        this.mNotes = mNotes;
        this.itemOnClick = itemOnClick;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, final int position) {
        final Note note = mNotes.get(position);
        holder.llItem.setBackgroundColor(note.getColor());
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getNote());
        holder.imgAlarm.setVisibility(note.isAlarm() ? View.VISIBLE : View.GONE);
        String date;
        try {
            date = DateFormatUtils.dateFormat(note.getNoteTime(), DateFormatUtils.DATE_TIME, DateFormatUtils.DATE_TIME_TYPE_2);
        } catch (ParseException e) {
            date = "";
        }
        holder.tvTimeAlarm.setText(date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnClick.onClick(note, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time_alarm)
        TextView tvTimeAlarm;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_alarm)
        ImageView imgAlarm;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public NoteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
