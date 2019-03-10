package com.example.mateup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private RecyclerView recyclerView;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mainToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Toolbar btn
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout , R.string.drawer_open, R.string.drawer_close );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View navHeader = navigationView.inflateHeaderView(R.layout.navigation_header);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);
                return false;


            }

        });

    }
    @Override
    //btn for drawer
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){

            return true;
        }
       return super.onOptionsItemSelected(item);
    }


    //drawer options
    private void UserMenuSelector(MenuItem menuItem)
    {

         switch (menuItem.getItemId())
         {
             case R.id.storyline:
                 SendUserToStoryLineActivity();
                 break;

             case R.id.myprofile:
                 SendUserToProfileActivity();
                 break;

             case R.id.messages:
                 Toast.makeText(this, "Going on messages", Toast.LENGTH_SHORT).show();
                 break;

             case R.id.settiings:
                 Toast.makeText(this, "Going on settings", Toast.LENGTH_SHORT).show();
                 break;

             case R.id.logout:
                openLoginActivity();
                 break;

         }

    }

    private void openLoginActivity() {
        Intent loginActivity = new Intent (this,LoginActivity.class);
        startActivity(loginActivity);
    }

    private void SendUserToStoryLineActivity() {
        Intent storyLine = new Intent(MainActivity.this,MainActivity.class);
        startActivity(storyLine);
    }

    private void SendUserToProfileActivity(){
        Intent loginIntent=new Intent(MainActivity.this,ProfileActivity.class);
        startActivity(loginIntent) ;
    }

    }

