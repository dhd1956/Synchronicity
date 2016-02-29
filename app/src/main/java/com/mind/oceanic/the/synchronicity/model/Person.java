package com.mind.oceanic.the.synchronicity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 1/21/16.
 */
public class Person implements Parcelable {
    private long personId;
    private String personFirstName;
    private String personLastName;
    private String personEmail;
    private String personMainPhone;
    private String personAddress;
    private String personCity;
    private String personProvince;
    private String personCountry;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonMainPhone() {
        return personMainPhone;
    }

    public void setPersonMainPhone(String personMainPhone) {
        this.personMainPhone = personMainPhone;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonCity() {
        return personCity;
    }

    public void setPersonCity(String personCity) {
        this.personCity = personCity;
    }

    public String getPersonProvince() {
        return personProvince;
    }

    public void setPersonProvince(String personProvince) {
        this.personProvince = personProvince;
    }

    public String getPersonCountry() {
        return personCountry;
    }

    public void setPersonCountry(String personCountry) {
        this.personCountry = personCountry;
    }


    @Override
    public String toString() {
        return personFirstName + " " + personLastName;
    }

    public Person() {

    }

    public Person(Parcel in) {
        personId = in.readLong();
        personFirstName = in.readString();
        personLastName = in.readString();
        personMainPhone = in.readString();
        personAddress = in.readString();
        personCity = in.readString();
        personProvince = in.readString();
        personCountry = in.readString();
        personEmail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(personId);
        dest.writeString(personFirstName);
        dest.writeString(personLastName);
        dest.writeString(personAddress);
        dest.writeString(personCity);
        dest.writeString(personProvince);
        dest.writeString(personCountry);
        dest.writeString(personEmail);
        dest.writeString(personMainPhone);
    }

    public static final Parcelable.Creator<Person> CREATOR =
            new Parcelable.Creator<Person>() {

                @Override
                public Person createFromParcel(Parcel source) {
                    return new Person(source);
                }

                @Override
                public Person[] newArray(int size) {
                    return new Person[size];
                }

            };
}

