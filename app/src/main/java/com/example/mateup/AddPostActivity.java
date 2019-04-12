package com.example.mateup;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mateup.services.RestClient;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddPostActivity extends AppCompatActivity {

    Bitmap bm;
    public ImageView postReview;
    public EditText description;
    public Button postBtn;
    private static final int SELECT_PICTURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);

        postReview = (ImageView) findViewById(R.id.postReview);
        description = (EditText) findViewById(R.id.description);
        postBtn = (Button) findViewById(R.id.post_btn);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIdAndUpload();
            }
        });

        GettingFromGallery();


    }



    private void GettingFromGallery() {




        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_PICTURE);
    }


     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {
             case SELECT_PICTURE:

                 if (resultCode == Activity.RESULT_OK) {
                     Uri selectedImage = data.getData();
                     String[] filePathColumn = { MediaStore.Images.Media.DATA };

                     Cursor cursor = getContentResolver().query(selectedImage,
                             filePathColumn, null, null, null);
                     cursor.moveToFirst();

                     int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                     String picturePath = cursor.getString(columnIndex);
                     cursor.close();


                     if (bm != null && bm.isRecycled()) {

                         bm = null;
                     }
                     bm = BitmapFactory.decodeFile(picturePath);
                     postReview.setImageBitmap(bm);
                     postReview.setImageURI(selectedImage);

                 }
         }


     }

    private void getIdAndUpload() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {



                try {
                    JSONObject postDescription = new JSONObject();
                    postDescription.put("content",description.getText());
                    RestClient rc = new RestClient("/posts");
                    String response = rc.executePost(postDescription.toString());
                    Log.i("content",response);


                    if (response != null) {
                        JSONObject res = new JSONObject(response);
                        String postId = res.getString("_id");
                        Log.i("post", postId);


                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(AddPostActivity.this);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("_id", postId);

                    }


                } catch (Exception e) {
                    Log.e("Filippppp", e.getMessage());
                    Log.e("Filippppp", e.toString());
                    Log.v("Filipppp", "Verbos");
                    e.printStackTrace();

                }


            }
        });t.start();upload();






    }



    private void upload() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AddPostActivity.this);
                String postId = pref.getString("_id", "not found");
                try {

                    String a = String.valueOf(description.getText());


                    // URL url = new URL(
                    String URL_BASE = "https://mateup.nstechlabs.com/api/files";

                    String url1 = String
                            .format(URL_BASE
                                            + "description_comment_=%s&member_id_comment_=%s&product_id_comment_=%s&store_id_comment_=%s",
                                    a, 1, 191, 55);

                    URL url = new URL(url1);

                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    // this HTTP request will involve input
                    c.addRequestProperty("app_key", "rg946A");
                    c.addRequestProperty("post_id",postId);
                    c.addRequestProperty("description", description.getText().toString());
                    // c.addRequestProperty("categories", SelcectItem);
                    c.setDoInput(true);
                    // should be PUT or POST to follow convention
                    c.setRequestMethod("POST");
                    System.out.println(c.getRequestProperties().toString());
                    // this HTTP request will involve output
                    c.setDoOutput(true);
                    // open the HTTP connection
                    c.connect();
                    OutputStream output = c.getOutputStream();
                    // compress and write the image to the output stream
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, output);

                    output.close();

                    System.out.println(c.getResponseMessage());
                    if (c.getResponseMessage().equalsIgnoreCase("OK")) {

                        Toast.makeText(
                                getApplicationContext(),
                                "Picture Upload Successfully..\n"
                                        + "Picture will be available after admin approval",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    // log error
                    e.printStackTrace();
                    Log.e("ImageUploader", "Error uploading image", e);
                }

            }
            });
        }



    }





