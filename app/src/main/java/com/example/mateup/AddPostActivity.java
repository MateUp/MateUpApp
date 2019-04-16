package com.example.mateup;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mateup.models.FileUpload;
import com.example.mateup.services.RestClient;
import com.ipaulpro.afilechooser.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPostActivity extends AppCompatActivity {


    Bitmap bm;
    public ImageView postReview;
    public EditText description;
    public Button postBtn;
    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);

        postReview = (ImageView) findViewById(R.id.postReview);
        description = (EditText) findViewById(R.id.description);
        postBtn = (Button) findViewById(R.id.post_btn);



        selectImage();

        postBtn.setOnClickListener(v -> createPostAndUploadImage());
    }



    private  void createPostAndUploadImage() {
        AsyncTask.execute(() -> {
            try {
                JSONObject postDescription = new JSONObject();
                postDescription.put("content", description.getText());
                RestClient rc = new RestClient("/posts");
                String response = rc.executePost(postDescription.toString());
                Log.i("content", response);


                if (response != null) {
                    JSONObject res = new JSONObject(response);
                    String postId = res.getString("_id");
                    Log.i("post", postId);

                    uploadImage(postId);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void uploadImage(final String postId) {

        Context applicationContext = LoginActivity.getContextOfApplication();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        final String token = pref.getString("token","not found");

        File fileObj = FileUtils.getFile(AddPostActivity.this, path);

        RequestBody postPart = RequestBody.create(MultipartBody.FORM, postId);
        RequestBody filePart = RequestBody.create(
            MediaType.parse(getContentResolver().getType(path)),
            fileObj
        );

        MultipartBody.Part file = MultipartBody.Part.createFormData("file", "file", filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://mateup.nstechlabs.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        FileUpload fileUpload = retrofit.create(FileUpload.class);

        Call<ResponseBody> call = fileUpload.uploadImage(token, postPart, file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("slika",response.toString());
                Toast.makeText(AddPostActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                Intent storyLine = new Intent(AddPostActivity.this,MainActivity.class);
                startActivity(storyLine);

        }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("slika",t.getMessage());

                t.printStackTrace();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            path = data.getData();

            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                postReview.setImageBitmap(bm);
                postReview.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}





