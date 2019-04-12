package com.example.mateup;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mateup.services.RestClient;

import org.json.JSONObject;

import java.io.IOException;

public class AddPostActivity extends AppCompatActivity {

    public ImageView postReview;
    public EditText description;
    public Button postBtn;
    private static final int RESULT_LOAD_IMAGE = 1;

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
        startActivityForResult(pickPhoto, RESULT_LOAD_IMAGE);
    }


     protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && imageReturnedIntent != null ) {

            Uri selectedImage = imageReturnedIntent.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                postReview.setImageBitmap(image);


            } catch (IOException e) {
                e.printStackTrace();
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
        });t.start();

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }


}
