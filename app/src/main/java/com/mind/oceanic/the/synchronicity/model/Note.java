package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 1/21/16.
 */
public class Note implements Parcelable {
    private long noteId;
    private long fkEventId;
    private String notePerson;
    private String notePlace;
    private String noteInfo;
    
    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getFkEventId() {
        return fkEventId;
    }

    public void setFkEventId(long fkEventId) {
        this.fkEventId = fkEventId;
    }

    public String getNotePerson() {
        return notePerson;
    }

    public void setNotePerson(String notePerson) {
        this.notePerson = notePerson;
    }

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    @Override
    public String toString() {
        return notePerson + ": " + noteInfo;
    }

    public Note() {

    }

    public Note(Parcel in) {
        noteId = in.readLong();
        fkEventId = in.readLong();
        notePerson = in.readString();
        noteInfo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
        dest.writeLong(fkEventId);
        dest.writeString(notePerson);
        dest.writeString(noteInfo);
    }

    public static final Creator<Note> CREATOR =
            new Creator<Note>() {

                @Override
                public Note createFromParcel(Parcel source) {
                    return new Note(source);
                }

                @Override
                public Note[] newArray(int size) {
                    return new Note[size];
                }

            };
}
