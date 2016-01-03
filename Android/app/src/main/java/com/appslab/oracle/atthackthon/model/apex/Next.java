package com.appslab.oracle.atthackthon.model.apex;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Osvaldo Villagrana on 12/29/15.
 */
public class Next implements Parcelable {
    private String $ref;

    public Next(String $ref) {
        this.$ref = $ref;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public static final Creator<Next> CREATOR = new Creator<Next>() {
        @Override
        public Next createFromParcel(Parcel in) {
            return new Next(in);
        }

        @Override
        public Next[] newArray(int size) {
            return new Next[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeString($ref);
    }

    private Next(Parcel in) {
        $ref = in.readString();
    }
}
