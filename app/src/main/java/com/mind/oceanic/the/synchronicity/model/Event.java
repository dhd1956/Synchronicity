package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by dave on 1/21/16.
 */
public class Event implements Parcelable {
    private long eventId;
    private String eventDate;
    private String eventSummary;
    private String eventDetails;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return eventSummary;
    }

    public Event() {

    }

    public Event(Parcel in) {
        eventId = in.readLong();
        eventDate = in.readString();
        eventSummary = in.readString();
        eventDetails = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(eventId);
        dest.writeString(eventDate);
        dest.writeString(eventSummary);
        dest.writeString(eventDetails);
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
