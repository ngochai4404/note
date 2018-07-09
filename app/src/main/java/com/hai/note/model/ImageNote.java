package com.hai.note.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hai on 06/07/2018.
 */

public class ImageNote implements Parcelable{
    private int id;
    private String path;

    public ImageNote() {

    }

    public ImageNote(String path) {
        this.path = path;
    }

    public ImageNote(int id, String path) {
        this.id = id;
        this.path = path;
    }

    protected ImageNote(Parcel in) {
        id = in.readInt();
        path = in.readString();
    }

    public static final Creator<ImageNote> CREATOR = new Creator<ImageNote>() {
        @Override
        public ImageNote createFromParcel(Parcel in) {
            return new ImageNote(in);
        }

        @Override
        public ImageNote[] newArray(int size) {
            return new ImageNote[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(path);
    }
}
