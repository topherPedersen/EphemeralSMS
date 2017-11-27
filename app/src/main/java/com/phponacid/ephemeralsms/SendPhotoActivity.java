package com.phponacid.ephemeralsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SendPhotoActivity extends AppCompatActivity {

    Button sendPhotoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);

        sendPhotoBtn = (Button) findViewById(R.id.sendPhotoBtn);
        sendPhotoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: write code to send photo

            }
        });
    }
}
