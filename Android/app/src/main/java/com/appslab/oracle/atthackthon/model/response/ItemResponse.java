package com.appslab.oracle.atthackthon.model.response;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.appslab.oracle.atthackthon.model.Item;
import com.appslab.oracle.atthackthon.model.apex.ApexBlueprint;
import com.appslab.oracle.atthackthon.model.apex.Next;

import java.util.ArrayList;

/**
 * Created by Osvaldo Villagrana on 12/29/15.
 */
public class ItemResponse extends ApexBlueprint {
    private ArrayList<Item> items;

    public ItemResponse(Next next, ArrayList<Item> items) {
        super(next);
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public static final Creator<ItemResponse> CREATOR = new Creator<ItemResponse>() {
        @Override
        public ItemResponse createFromParcel(Parcel in) {
            return new ItemResponse(in);
        }

        @Override
        public ItemResponse[] newArray(int size) {
            return new ItemResponse[size];
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

    private ItemResponse(Parcel in) {
        super(in);
        items = in.readArrayList(Item.class.getClassLoader());
    }
}
