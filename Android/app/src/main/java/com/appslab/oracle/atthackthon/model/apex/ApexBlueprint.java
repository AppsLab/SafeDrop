package com.appslab.oracle.atthackthon.model.apex;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Osvaldo Villagrana on 12/29/15.
 */
public class ApexBlueprint implements Parcelable {
    private Next next;

    public ApexBlueprint(Next next) {
        this.next = next;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public static final Creator<ApexBlueprint> CREATOR = new Creator<ApexBlueprint>() {
        @Override
        public ApexBlueprint createFromParcel(Parcel in) {
            return new ApexBlueprint(in);
        }

        @Override
        public ApexBlueprint[] newArray(int size) {
            return new ApexBlueprint[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeParcelable(next, flags);
    }

    protected ApexBlueprint(Parcel in) {
        next = in.readParcelable(Next.class.getClassLoader());
    }
}
