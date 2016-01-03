package com.appslab.oracle.atthackthon.model.response;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.appslab.oracle.atthackthon.model.User;
import com.appslab.oracle.atthackthon.model.apex.ApexBlueprint;
import com.appslab.oracle.atthackthon.model.apex.Next;

import java.util.ArrayList;

/**
 * Created by Osvaldo Villagrana on 12/29/15.
 */
public class UserResponse extends ApexBlueprint {
    private ArrayList<User> items;

    public UserResponse(Next next, ArrayList<User> items) {
        super(next);
        this.items = items;
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeList(items);
    }

    private UserResponse(Parcel in) {
        super(in);
        items = in.readArrayList(User.class.getClassLoader());
    }
}
