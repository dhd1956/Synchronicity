package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dave on 1/21/16.
 */
public class Verb implements Parcelable {
    // borrow; return; buy; sell; throwOut
    private long verbId;
    private String verbName;
    private String verbAppliesTo;

    public long getVerbId() {
        return verbId;
    }

    public void setVerbId(long verbId) {
        this.verbId = verbId;
    }

    public String getVerbName() {
        return verbName;
    }

    public void setVerbName(String verbName) {
        this.verbName = verbName;
    }

    public String getVerbAppliesTo() {
        return verbAppliesTo;
    }

    public void setVerbAppliesTo(String verbAppliesTo) {
        this.verbAppliesTo = verbAppliesTo;
    }

    @Override
    public String toString() {
            return verbName;

    }

    public Verb() {

    }

    public Verb(Parcel in) {
        verbId = in.readLong();
        verbName = in.readString();
        verbAppliesTo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(verbId);
        dest.writeString(verbName);
        dest.writeString(verbAppliesTo);
    }

    public static final Parcelable.Creator<Verb> CREATOR =
            new Parcelable.Creator<Verb>() {

                @Override
                public Verb createFromParcel(Parcel source) {
                    return new Verb(source);
                }

                @Override
                public Verb[] newArray(int size) {
                    return new Verb[size];
                }

            };
}
