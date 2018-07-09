package com.hai.note.custom.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hai.note.BuildConfig;
import com.hai.note.R;
import com.hai.note.model.ImageNote;
import com.hai.note.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hai on 06/07/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{
    private Context mContext;
    private List<ImageNote> list;

    public ImageAdapter(Context mContext, List<ImageNote> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public List<ImageNote> getList() {
        return list;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_image_note,parent,false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, final int position) {
        holder.imgNote.setImageBitmap(FileUtils.decodeFile(new File(list.get(position).getPath())));
        holder.ibtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_note)
        ImageView imgNote;
        @BindView(R.id.ibtn_delete)
        ImageButton ibtnDel;
        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
