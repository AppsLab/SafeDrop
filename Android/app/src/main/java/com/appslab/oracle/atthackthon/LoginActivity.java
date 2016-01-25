package com.appslab.oracle.atthackthon;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appslab.oracle.HttpConnect;
import com.appslab.oracle.ServiceResponse;
import com.appslab.oracle.atthackthon.model.User;
import com.appslab.oracle.atthackthon.model.response.UserResponse;
import com.datami.smi.SdState;
import com.datami.smi.SdStateChangeListener;
import com.datami.smi.SmiResult;
import com.datami.smi.SmiSdk;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements SdStateChangeListener {
    private final String API = "dmi-att-hack-68fcfe5e708bfaa3806c4888912ea6f2ecb446fd";
    @Bind(R.id.layout)
    protected LinearLayout rl;

    @Bind(R.id.forgot_password)
    protected TextView forgotPassword;

    @Bind(R.id.login_button)
    protected Button loginButton;

    @Bind(R.id.progressBar)
    protected ProgressBar progressBar;

    @BindColor(R.color.from)
    int colorFrom;

    @BindColor(R.color.from)
    int colorTo;

    private final int animationDuration = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ButterKnife.bind(this);
        setupBackground();
        setupViews();
        SmiSdk.getAppSDAuth(API, getApplicationContext(), "", -1, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SmiSdk.appVisibility(this, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SmiSdk.appVisibility(this, true);
    }

    @OnClick(R.id.login_button)
    public void attemptLogin() {
        //new UserLogin().execute();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void setupViews() {
        String source = "¿Olvidaste la información de inicio de sesión? " + "<b>Obten ayuda para iniciar sesión</b>" + ".";
        forgotPassword.setText(Html.fromHtml(source));
    }

    private void setupBackground() {
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(animationDuration);
        colorAnimation.setEvaluator(new ArgbEvaluator());
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                rl.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    @Override
    public void onChange(SmiResult currentSmiResult) {
        SdState sdState = currentSmiResult.getSdState();

        if (sdState == SdState.SD_AVAILABLE) {

        } else if (sdState == SdState.SD_NOT_AVAILABLE) {

        } else if(sdState == SdState.WIFI) {

        }
    }

    private class UserLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            loginButton.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpConnect conn = HttpConnect.getInstance();
            ServiceResponse response = conn
                    .post(new User("noelportugal@gmail.com", "sd3t0n"))
                    .request("https://apex.oracle.com/pls/apex/m2m/authenticate", UserResponse.class, null);

            if (response.getObjectResponse() != null) {
                UserResponse userResponse = (UserResponse) response.getObjectResponse();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
