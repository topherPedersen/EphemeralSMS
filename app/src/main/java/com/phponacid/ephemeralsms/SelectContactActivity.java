package com.phponacid.ephemeralsms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectContactActivity extends AppCompatActivity {

    Button selectContactBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        Intent intent = getIntent();
        String extraUrlReceived = intent.getStringExtra("EXTRA_URL");
        Toast.makeText(getApplicationContext(),extraUrlReceived,
                Toast.LENGTH_SHORT).show();

        selectContactBtn = (Button) findViewById(R.id.selectContactBtn);
        selectContactBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: write code to select contact
                launchSendPhotoActivity();
            }
        });
    }

    public void launchSendPhotoActivity() {
        Intent i = new Intent(this, SendPhotoActivity.class);
        // TODO: pass data via intent-extra
        // i.putExtra(EXTRA_FOO, "bar");
        startActivity(i);
    }

    /*
    public void displayUrlAsToast() {
        Context context = getApplicationContext();
        CharSequence text = extraUrl;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    */
}
