package com.appslab.oracle.atthackthon;

import android.app.Application;
import android.os.SystemClock;

import com.datami.smi.SdState;
import com.datami.smi.SdStateChangeListener;
import com.datami.smi.SmiResult;
import com.datami.smi.SmiSdk;

import java.util.concurrent.TimeUnit;

/**
 * Created by Osvaldo Villagrana on 12/23/15.
 */
public class App extends Application implements SdStateChangeListener {
    private final String API = "dmi-att-hack-68fcfe5e708bfaa3806c4888912ea6f2ecb446fd";

    @Override
    public void onCreate() {
        super.onCreate();

        SmiSdk.getAppSDAuth(API, getApplicationContext(), "", -1, true);
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }

    @Override
    public void onChange(SmiResult currentSmiResult) {
        SdState sdState = currentSmiResult.getSdState();

        if (sdState == SdState.SD_AVAILABLE) {
        } else if (sdState == SdState.SD_NOT_AVAILABLE) {

        } else if(sdState == SdState.WIFI) {

        }
    }
}
