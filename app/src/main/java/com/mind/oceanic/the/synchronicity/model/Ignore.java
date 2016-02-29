package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 2/9/16.
 */
public class Ignore implements Parcelable {

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    private String word;
    @Override
    public String toString() {
        return word;
    }

    public Ignore() {

    }

    public Ignore(Parcel in) {
        word = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);

    }

    public static final Parcelable.Creator<Event> CREATOR =
            new Parcelable.Creator<Event>() {

                @Override
                public Event createFromParcel(Parcel source) {
                    return new Event(source);
                }

                @Override
                public Event[] newArray(int size) {
                    return new Event[size];
                }

            };
}
