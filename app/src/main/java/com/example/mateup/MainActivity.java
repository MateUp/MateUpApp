package com.example.mateup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private NavigationView navigationView;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mainToolbar;
    public ImageView imageView;
    public static Context contextOfApplication;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ArrayList<UsersPosts> postList;
    private RequestQueue requestQueue;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.contextOfApplication = getApplicationContext();

        recyclerView = findViewById(R.id.all_users_post_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();


        mainToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageView = (ImageView) findViewById(R.id.post_image);



        //Toolbar btn
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View navHeader = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView drawer_username = (TextView) navHeader.findViewById(R.id.drawer_username);




        //set drawer profile info
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String firstName = pref.getString("firstName", "not found");
        String lastName = pref.getString("lastName", "not found");
        drawer_username.setText(firstName + " " + lastName);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);
                return false;

            }

        });






    }

    private void parseJSON() {
        String Url = "https://mateup.nstechlabs.com/api/posts";

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String token = pref.getString("token", "not found");





        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null,
                new Response.Listener<JSONArray>() {


                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {




                        try {


                            Log.i("proba", String.valueOf(response));



                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject post = response.getJSONObject(i);
                                String postDescription = post.getString("content");
                                String postDate = post.getString("createdAt");
                                JSONObject user = post.getJSONObject("user");
                                String firstName = user.getString("firstName");
                                String lastName = user.getString("lastName");
                                String profession = user.getString("profession");


                                if(post.has("photo") ){
                                JSONObject photo = post.getJSONObject("photo");
                                String imageUrl = photo.getString("publicUrl");

                                    postList.add(new UsersPosts(imageUrl,postDescription,postDate,firstName,lastName,profession));

                                }else {
                                    i = i++;

                                }





                            }
                            postAdapter = new PostAdapter(MainActivity.this , postList);
                            recyclerView.setAdapter(postAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("stae",e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("proba",error.getMessage());
            }
        }){ public Map getHeaders() throws AuthFailureError {
            HashMap headers = new HashMap();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization",token);
            return headers;
        }};requestQueue.add(jsonArrayRequest);
    }


    @Override
    //btn for drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);


    }


    void UserMenuSelector(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.storyline:
                SendUserToStoryLineActivity();
                break;

            case R.id.myprofile:
                SendUserToProfileActivity();
                break;

            case R.id.messages:
                Toast.makeText(this, "Going on messages", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_post:
                pickFromGallery();
                break;

            case R.id.logout:
                openLoginActivity();
                break;

        }

    }


    private void pickFromGallery() {

        Intent postActivity = new Intent (this,AddPostActivity.class);
        startActivity(postActivity);
    }

    private void openLoginActivity() {
        Intent loginActivity = new Intent (this,LoginActivity.class);
        startActivity(loginActivity);
    }

    private void SendUserToStoryLineActivity() {
        Intent storyLine = new Intent(MainActivity.this,MainActivity.class);
        startActivity(storyLine);
    }

    private void SendUserToProfileActivity() {
        Intent loginIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(loginIntent);
    }






}

