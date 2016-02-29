package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 1/21/16.
 */
public class Place implements Parcelable {
    private long placeId;
    private String placeName;
    private String placeEmail;
    private String placeMainPhone;
    private String placeAddress;
    private String placeCity;
    private String placeProvince;
    private String placeCountry;

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceEmail() {
        return placeEmail;
    }

    public void setPlaceEmail(String placeEmail) {
        this.placeEmail = placeEmail;
    }

    public String getPlaceMainPhone() {
        return placeMainPhone;
    }

    public void setPlaceMainPhone(String placeMainPhone) {
        this.placeMainPhone = placeMainPhone;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(String placeProvince) {
        this.placeProvince = placeProvince;
    }

    public String getPlaceCountry() {
        return placeCountry;
    }

    public void setPlaceCountry(String placeCountry) {
        this.placeCountry = placeCountry;
    }


    @Override
    public String toString() {
        return placeName;
    }

    public Place() {

    }

    public Place(Parcel in) {
        placeId = in.readLong();
        placeName = in.readString();
        placeAddress = in.readString();
        placeCity = in.readString();
        placeProvince = in.readString();
        placeCountry = in.readString();
        placeEmail = in.readString();
        placeMainPhone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(placeId);
        dest.writeString(placeName);
        dest.writeString(placeAddress);
        dest.writeString(placeCity);
        dest.writeString(placeProvince);
        dest.writeString(placeCountry);
        dest.writeString(placeEmail);
        dest.writeString(placeMainPhone);
    }

    public static final Parcelable.Creator<Place> CREATOR =
            new Parcelable.Creator<Place>() {

                @Override
                public Place createFromParcel(Parcel source) {
                    return new Place(source);
                }

                @Override
                public Place[] newArray(int size) {
                    return new Place[size];
                }

            };
}
