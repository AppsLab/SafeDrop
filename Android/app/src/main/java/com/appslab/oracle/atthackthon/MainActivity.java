package com.appslab.oracle.atthackthon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.appslab.oracle.HttpConnect;
import com.appslab.oracle.ServiceResponse;
import com.appslab.oracle.atthackthon.model.Item;
import com.appslab.oracle.atthackthon.model.adapter.DividerItemDecoration;
import com.appslab.oracle.atthackthon.model.adapter.ItemAdapter;
import com.appslab.oracle.atthackthon.model.response.ItemResponse;
import com.appslab.oracle.atthackthon.recycler.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recycler)
    protected RecyclerView recycler;

    @Bind(R.id.snackbarCoordinatorLayout)
    protected CoordinatorLayout coordinatorLayout;

    protected RecyclerView.LayoutManager mLayoutManager;
    protected ItemAdapter mAdapter;
    protected List<Item> mDataset;

    private boolean open = false;
    private boolean close = false;

    public MainActivity() {
        mDataset = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new ItemAdapter(getApplicationContext(), mDataset);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(mAdapter);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                if (mDataset.get(position).getStatus().equalsIgnoreCase("delivered")) {
                    intent = new Intent(getBaseContext(), VideoActivity.class);
                } else {
                    intent = new Intent(getBaseContext(), DetailActivity.class);
                }
                intent.putExtra("ITEM", mDataset.get(position));
                startActivity(intent);
            }
        }));

        new GetItem().execute();
        //setupClient();
        testNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new OpenGate().execute();

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            mDataset.add((Item)data.getParcelableExtra("EXTRA"));
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fab)
    public void add() {
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        startActivityForResult(intent, 1);
    }

    private class OpenGate extends AsyncTask<Void, Void, ItemResponse> {

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ItemResponse doInBackground(Void... voids) {
            ItemResponse itemResponse = null;

            HttpConnect conn = HttpConnect.getInstance();
            ServiceResponse response = conn
                    .request("http://192.168.1.185:8086/lock", null, null);

            if (response.getObjectResponse() != null) {
                itemResponse = (ItemResponse) response.getObjectResponse();
            }
            return itemResponse;
        }

        @Override
        protected void onPostExecute(ItemResponse response) {
            Snackbar.make(coordinatorLayout, "Open SafeDrop gate", Snackbar.LENGTH_SHORT).show();
        }
    }

    private class GetItem extends AsyncTask<Void, Void, ItemResponse> {

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ItemResponse doInBackground(Void... voids) {
            ItemResponse itemResponse = null;

            HttpConnect conn = HttpConnect.getInstance();
            ServiceResponse response = conn
                    .request("https://apex.oracle.com/pls/apex/m2m/safedrop", ItemResponse.class, null);

            if (response.getObjectResponse() != null) {
                itemResponse = (ItemResponse) response.getObjectResponse();
            }
            return itemResponse;
        }

        @Override
        protected void onPostExecute(ItemResponse response) {
            for (Item item : response.getItems()) {
                mDataset.add(item);
            }
            mAdapter.notifyDataSetChanged();
            Snackbar.make(coordinatorLayout, "Packages updated", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void testNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_box)
                        .setContentTitle("SafeDrop")
                        .setContentText("You have received a new package!");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, mBuilder.build());

    }

    private void changeItem() {
        for (int i = 0; i < mDataset.size(); i++) {
            if (mDataset.get(i).getStatus().equalsIgnoreCase("Waiting")) {
                Item item = mDataset.get(i);
                mDataset.remove(i);
                mAdapter.notifyDataSetChanged();

//                item.setStatus("delivered");
//                mDataset.add(item);
//                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
