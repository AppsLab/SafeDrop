package com.appslab.oracle.atthackthon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appslab.oracle.atthackthon.model.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.item_name)
    protected TextView itemName;

    @Bind(R.id.delivery_company)
    protected TextView deliveryComany;

    @Bind(R.id.tracking_number)
    protected TextView trackingNumber;

    @Bind(R.id.country)
    protected TextView country;

    @Bind(R.id.status)
    protected TextView status;

    @Bind(R.id.arrival_date)
    protected TextView arrivalDate;

    @Bind(R.id.arrival_time)
    protected TextView arrivalTime;

    @Bind(R.id.fab)
    protected FloatingActionButton floatingActionButton;

    private final String FEDEX = "fedex";
    private final String UPS = "ups";
    private final String DHL = "dhl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My package");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Item item = getIntent().getExtras().getParcelable("ITEM");

        ButterKnife.bind(this);
        populateItem(item);
    }

    private void populateItem(Item item) {
        itemName.setText(item.getItem_name());
        deliveryComany.setText(item.getDelivery_company());
        trackingNumber.setText(item.getTrack_number());
        //country.setText(item.);
        status.setText(item.getStatus());
        arrivalDate.setText(item.getTime());
        //arrivalTime.setText(item);

        switch (item.getDelivery_company().toLowerCase()) {
            case FEDEX:
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fedex));
                break;
            case UPS:
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ups));
                break;
            case DHL:
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dhl));
                break;
            default: break;
        }
    }
}
