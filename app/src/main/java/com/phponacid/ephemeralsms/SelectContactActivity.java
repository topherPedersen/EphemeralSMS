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
    String extraUrlReceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        Intent intent = getIntent();
        extraUrlReceived = intent.getStringExtra("EXTRA_URL");
        Toast.makeText(getApplicationContext(),extraUrlReceived,
                Toast.LENGTH_SHORT).show();

        selectContactBtn = (Button) findViewById(R.id.selectContactBtn);
        selectContactBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: write code to select contact
                launchAddressBookActivity();
            }
        });
    }

    public void launchAddressBookActivity() {
        Intent i = new Intent(this, AddressBookActivity.class);
        // TODO: pass data via intent-extra
        i.putExtra("URL_EXTRA", extraUrlReceived);
        startActivity(i);
        finish(); // kill activity after new activity launches
    }
}
