package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 1/21/16.
 */
public class Thing implements Parcelable {
    private long thingId;
    private String thingName;
    private String thingUse;

    public long getThingId() {
        return thingId;
    }

    public void setThingId(long thingId) {
        this.thingId = thingId;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getThingUse() {
        return thingUse;
    }

    public void setThingUse(String thingUse) {
        this.thingUse = thingUse;
    }
    @Override
    public String toString() {
        String _Date = "2010-09-29 08:45:22";
        if (thingName.equals(null)) {
            return "null";
        } else {
            return thingName;
        }
    }

    public Thing() {

    }

    public Thing(Parcel in) {
        thingId = in.readLong();
        thingName = in.readString();
        thingUse = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(thingId);
        dest.writeString(thingName);
        dest.writeString(thingUse);
    }

    public static final Parcelable.Creator<Thing> CREATOR =
            new Parcelable.Creator<Thing>() {

                @Override
                public Thing createFromParcel(Parcel source) {
                    return new Thing(source);
                }

                @Override
                public Thing[] newArray(int size) {
                    return new Thing[size];
                }

            };
}
