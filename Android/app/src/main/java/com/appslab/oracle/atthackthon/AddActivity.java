package com.appslab.oracle.atthackthon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.appslab.oracle.HttpConnect;
import com.appslab.oracle.ServiceResponse;
import com.appslab.oracle.atthackthon.model.Item;
import com.appslab.oracle.atthackthon.model.response.ItemResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends AppCompatActivity {
    @Bind(R.id.add_button)
    protected Button add;

    @Bind(R.id.item_name)
    TextView item_name;

    @Bind(R.id.delivery_company)
    TextView deliveryCompany;

    @Bind(R.id.tracking_id)
    TextView trackingId;

    @Bind(R.id.country)
    TextView country;

    @Bind(R.id.status)
    TextView status;

    @Bind(R.id.arrival_date)
    TextView arrivalDate;

    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_button)
    public void add() {
        item = new Item(
                item_name.getText().toString(),
                trackingId.getText().toString(),
                "",
                arrivalDate.getText().toString(),
                "",
                status.getText().toString(),
                deliveryCompany.getText().toString(), "US");

        new AddItem().execute();
    }

    private class AddItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpConnect conn = HttpConnect.getInstance();
            ServiceResponse response = conn
                    .post(item)
                    .request("https://apex.oracle.com/pls/apex/m2m/safedrop", ItemResponse.class, null);

            if (response.getObjectResponse() != null) {
                ItemResponse userResponse = (ItemResponse) response.getObjectResponse();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            Intent intent = new Intent();
            intent.putExtra("EXTRA", new Item(
                    item_name.getText().toString(),
                    trackingId.getText().toString(),
                    "",
                    arrivalDate.getText().toString(),
                    "",
                    status.getText().toString(),
                    deliveryCompany.getText().toString(), "US")
                    );
            setResult(1, intent);
            finish();
        }
    }
}
