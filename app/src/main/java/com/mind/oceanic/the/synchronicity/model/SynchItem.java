package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by dave on 1/18/16.
 */
public class SynchItem implements  Parcelable {
    private long synchId;
    private String synchDate;
    private String synchSummary;
    private String synchDetails;
    private int synchRating;


    public long getSynchId() {
        return synchId;
    }

    public void setSynchId(long synchId) {
        this.synchId = synchId;
    }

    public String getSynchDate() {
        return synchDate;
    }

    public void setSynchDate(String synchDate) {
        this.synchDate = synchDate;
    }

    public String getSynchSummary() {
        return synchSummary;
    }

    public void setSynchSummary(String summary) {
        this.synchSummary = summary;
    }

    public String getSynchDetails() {
        return synchDetails;
    }

    public void setSynchDetails(String details) {
        this.synchDetails = details;
    }

    public int getSynchRating() {
        return synchRating;
    }

    public void setSynchRating(int synchRating) {
        this.synchRating = synchRating;
    }

    @Override
    public String toString() {
        return synchSummary;
    }

    public SynchItem() {

    }

    public SynchItem(Parcel in) {
        synchId = in.readLong();
        synchDate = in.readString();
        synchSummary = in.readString();
        synchDetails = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(synchId);
        dest.writeString(synchDate);
        dest.writeString(synchSummary);
        dest.writeString(synchDetails);
    }

    public static final Parcelable.Creator<SynchItem> CREATOR =
            new Parcelable.Creator<SynchItem>() {

                @Override
                public SynchItem createFromParcel(Parcel source) {
                    return new SynchItem(source);
                }

                @Override
                public SynchItem[] newArray(int size) {
                    return new SynchItem[size];
                }

            };
}
