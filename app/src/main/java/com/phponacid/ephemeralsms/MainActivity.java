package com.phponacid.ephemeralsms;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.bitmap;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static final int PERMISSION_ALL = 0;

    Button takePhotoBtn;
    Button encodeImageBtn;
    Button displayMemoryBtn;
    File photoFile;
    Uri photoURI;
    String mCurrentPhotoPath;
    Bitmap bitmap;
    String encoded_string;
    String urlExtra;
    String image_name = "imageFromAndroidApp.jpg";
    String debugStr = "";
    File file;

    InputStream targetStream;

    Boolean permissionGranted = false;

    String bitmapDebugStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askDeviceForPermission();

        takePhotoBtn = (Button) findViewById(R.id.takePhotoBtn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: write code to take photo
                takePhoto();
                // launchSelectContactActivity();
            }
        });

    }

    public void askDeviceForPermission() {
        // ASK FOR PERMISSION TO SEND SMS MESSAGES
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS},
                        PERMISSION_ALL);
            }
        } else {
            permissionGranted = true;
        }
        if (permissionGranted == false) {
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // File image = File.createTempFile(
        //        imageFileName,  /* prefix */
        //        ".jpg",         /* suffix */
        //        storageDir      /* directory */

        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + imageFileName
        );

        photoURI = Uri.fromFile(file);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // photoURI = FileProvider.getUriForFile(this, "com.phponacid.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                // TODO: encode image after photo is taken and saved
                new ImageEncoder().execute();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private class ImageEncoder extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                targetStream = new FileInputStream(photoFile);
                bitmap = BitmapFactory.decodeStream(targetStream);
                // bitmap = BitmapFactory.decodeFile(photoURI.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (photoFile == null) {
                debugStr += "photoFile == null\n";
            }
            if (targetStream == null) {
                debugStr += "targetStream == null\n";
            }
            if (bitmap == null) {
                debugStr += "bitmap == null\n";
            }

            /*
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap.recycle();
            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            */

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            TextView debugTextView = (TextView) findViewById(R.id.debugTextView);
            debugTextView.setText(debugStr);
            // makeRequest method commented-out for debugging purposes (Android Lollipop & Earlier)
            // makeRequest();
        }
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://EphemeralSMS.com/generate_message.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        urlExtra = "" + response + "";

                        Intent i = new Intent(getApplicationContext(), SelectContactActivity.class);
                        // TODO: pass data via intent-extra
                        i.putExtra("EXTRA_URL", urlExtra);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("encoded_string", encoded_string);
                map.put("image_name", image_name);
                return map;
            }
        };
        requestQueue.add(request);
    }

}
