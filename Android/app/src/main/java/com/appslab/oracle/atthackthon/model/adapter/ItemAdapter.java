package com.appslab.oracle.atthackthon.model.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appslab.oracle.atthackthon.R;
import com.appslab.oracle.atthackthon.model.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Osvaldo Villagrana on 1/2/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Item> mDataSet;
    private final String FEDEX = "fedex";
    private final String UPS = "ups";
    private final String DHL = "dhl";
    private final String DELIVERED = "delivered";
    private final String WAITING = "waiting";
    private final String WARNING = "warning";

    public ItemAdapter(Context context, List<Item> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup vGroup = (ViewGroup) mInflater.inflate(R.layout.item, parent, false);
        ViewHolder group = new ViewHolder(vGroup);

        return group;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = mDataSet.get(position);
        ViewHolder group = holder;

        populateItem(group, position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void populateItem(ViewHolder viewHolder, final int position) {
        Item item = mDataSet.get(position);

        viewHolder.getItem().setText(item.getItem_name());
        viewHolder.getDate().setText(item.getTime());
        switch (item.getDelivery_company().toLowerCase()) {
            case FEDEX:
                viewHolder.getDeliveryCompany().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fedex));
                break;
            case UPS:
                viewHolder.getDeliveryCompany().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ups));
                break;
            case DHL:
                viewHolder.getDeliveryCompany().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dhl));
                break;
            default: break;
        }

        switch (item.getStatus().toLowerCase()) {
            case DELIVERED:
                viewHolder.getStatus().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_ok));
                break;
            case WAITING:
                //viewHolder.getStatus().setVisibility(View.GONE);
                break;
            case WARNING:
                viewHolder.getStatus().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_wrong));
                break;
            default: break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delivery_company)
        ImageView deliveryCompany;
        @Bind(R.id.item)
        TextView item;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.status)
        ImageView status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getDeliveryCompany() {
            return deliveryCompany;
        }

        public TextView getItem() {
            return item;
        }

        public TextView getDate() {
            return date;
        }

        public ImageView getStatus() {
            return status;
        }
    }
}
