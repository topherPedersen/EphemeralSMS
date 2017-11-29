package com.phponacid.ephemeralsms;

import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SendPhotoActivity extends AppCompatActivity {

    ImageView sendPhotoBtn;
    String returnedUrlExtra;
    String returnedPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_send_photo);

        TextView activitySendPhotoH1 = (TextView) findViewById(R.id.activitySendPhotoH1);
        TextView activitySendPhotoH2 = (TextView) findViewById(R.id.activitySendPhotoH2);
        TextView activitySendPhotoH3 = (TextView) findViewById(R.id.activitySendPhotoH3);
        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/chunkfive.otf");
        activitySendPhotoH1.setTypeface(typeFace);
        activitySendPhotoH2.setTypeface(typeFace);
        activitySendPhotoH3.setTypeface(typeFace);

        returnedUrlExtra = getIntent().getStringExtra("RETURNED_URL_EXTRA");
        returnedPhoneNumber = getIntent().getStringExtra("RETURNED_PHONE_NUMBER");
        returnedPhoneNumber = returnedPhoneNumber.replaceAll("[^0-9]", ""); // bug fix: removes non-numerical chars from phoneNumber


        sendPhotoBtn = (ImageView) findViewById(R.id.sendPhotoBtn);
        sendPhotoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: write code to send photo
                sendSMS(); // send text message
            }
        });
    }

    public void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(returnedPhoneNumber, null, returnedUrlExtra, null, null);
        Toast.makeText(getApplicationContext(), "SMS Sent :)", Toast.LENGTH_LONG).show();

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                // do nothing
            }

            public void onFinish() {
                finish(); // After 2 seconds (2000 milliseconds), kill the app
            }
        }.start();

    }
}
