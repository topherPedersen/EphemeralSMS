package com.phponacid.ephemeralsms;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SendPhotoActivity extends AppCompatActivity {

    Button sendPhotoBtn;
    String returnedUrlExtra;
    String returnedPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);

        returnedUrlExtra = getIntent().getStringExtra("RETURNED_URL_EXTRA");
        returnedPhoneNumber = getIntent().getStringExtra("RETURNED_PHONE_NUMBER");
        returnedPhoneNumber = returnedPhoneNumber.replaceAll("[^0-9]", ""); // bug fix: removes non-numerical chars from phoneNumber


        sendPhotoBtn = (Button) findViewById(R.id.sendPhotoBtn);
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

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                // do nothing
            }

            public void onFinish() {
                finish(); // After 3 seconds (3000 milliseconds), kill the app
            }
        }.start();

    }
}
