package com.appslab.oracle.atthackthon;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.appslab.oracle.atthackthon.model.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {
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

    @Bind(R.id.video)
    protected VideoView videoview;

    private final String FEDEX = "fedex";
    private final String UPS = "ups";
    private final String DHL = "dhl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("My package video");
//        setSupportActionBar(toolbar);

        Item item = getIntent().getExtras().getParcelable("ITEM");

        ButterKnife.bind(this);
        populateItem(item);
        reproduce();
    }

    private void populateItem(Item item) {
        itemName.setText(item.getItem_name());
        deliveryComany.setText(item.getDelivery_company());
        trackingNumber.setText(item.getTrack_number());
        //country.setText(item.);
        status.setText(item.getStatus());
        arrivalDate.setText(item.getTime());
        //arrivalTime.setText(item);
    }

    public void reproduce() {
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(VideoActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse("http://192.168.1.185:8080/record.mp4");
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
            }
        });
    }
}
