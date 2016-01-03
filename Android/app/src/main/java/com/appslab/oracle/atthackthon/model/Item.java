package com.appslab.oracle.atthackthon.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Osvaldo Villagrana on 1/2/16.
 */
public class Item implements Parcelable {
    private String item_name;
    private String track_number;
    private String description;
    private String time;
    private String video_url;
    private String status;
    private String delivery_company;
    private String country;

    public Item(String item_name, String track_number, String description, String time, String video_url, String status, String delivery_company, String country) {
        this.item_name = item_name;
        this.track_number = track_number;
        this.description = description;
        this.time = time;
        this.video_url = video_url;
        this.status = status;
        this.delivery_company = delivery_company;
        this.country = country;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getTrack_number() {
        return track_number;
    }

    public void setTrack_number(String track_number) {
        this.track_number = track_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_company() {
        return delivery_company;
    }

    public void setDelivery_company(String delivery_company) {
        this.delivery_company = delivery_company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeString(item_name);
        out.writeString(track_number);
        out.writeString(description);
        out.writeString(time);
        out.writeString(video_url);
        out.writeString(status);
        out.writeString(delivery_company);
        out.writeString(country);
    }

    private Item(Parcel in) {
        item_name = in.readString();
        track_number = in.readString();
        description = in.readString();
        time = in.readString();
        video_url = in.readString();
        status = in.readString();
        delivery_company = in.readString();
        country = in.readString();
    }
}
