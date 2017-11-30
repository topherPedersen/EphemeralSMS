package com.phponacid.ephemeralsms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by topherpedersen on 11/27/17.
 */


public class ContactAdapter extends ArrayAdapter<Contact> {

    Context mContext;
    String mUrlExtra;

    public ContactAdapter(Activity contextParam, ArrayList<Contact> contactParam, String urlExtraParam) {
        // do stuff
        super(contextParam, 0, contactParam);
        mContext = contextParam;
        mUrlExtra = urlExtraParam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // do stuff

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.contact_linear_layout, parent, false);
        }

        // NOTE: added keyword 'final' to line of code below to fix a bug
        final Contact currentContact = getItem(position);
        final String phoneNumber = currentContact.getPhoneNumber();

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.nameTextView);
        TextView phoneNumberTextView = (TextView) listItemView.findViewById(R.id.phoneNumberTextView);
        LinearLayout contactLinearLayout = listItemView.findViewById(R.id.contactLinearLayout);

        nameTextView.setText(currentContact.getName());
        phoneNumberTextView.setText(currentContact.getPhoneNumber());

        Typeface typeFace= Typeface.createFromAsset(mContext.getAssets(),"fonts/chunkfive.otf");
        nameTextView.setTypeface(typeFace);
        phoneNumberTextView.setTypeface(typeFace);

        contactLinearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String digits = currentContact.getPhoneNumber().toString();
                // Code here executes on main thread after user presses button
                Intent sendPhotoIntent = new Intent(mContext, SendPhotoActivity.class);
                sendPhotoIntent.putExtra("RETURNED_PHONE_NUMBER", phoneNumber);
                sendPhotoIntent.putExtra("RETURNED_URL_EXTRA", mUrlExtra);
                mContext.startActivity(sendPhotoIntent);
                ((Activity)mContext).finish(); // kill activity after new activity launches
            }
        });



        return listItemView;
    }
}