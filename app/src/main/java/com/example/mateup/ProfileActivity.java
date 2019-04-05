package com.example.mateup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mateup.FragmentsCardsForProfile.BiographyFragment;
import com.example.mateup.FragmentsCardsForProfile.PartnersFragment;
import com.example.mateup.FragmentsCardsForProfile.PostsFragment;
import com.example.mateup.services.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView name_of_user, profession,bio_user_name;
    private CircleImageView new_profile_image;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText displayName;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        name_of_user=(TextView) findViewById(R.id.name_of_user);
        profession=(TextView) findViewById(R.id.profession);
        new_profile_image=(CircleImageView) findViewById(R.id.new_profileImage);
        bio_user_name = (TextView) findViewById(R.id.bio_user_name);

        Thread setProfileInfo = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                final String firstName = pref.getString("firstName","not found");
                final String lastName = pref.getString("lastName","not found");
                final String prf = pref.getString("profession","not found");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name_of_user.setText(firstName + " " + lastName);
                        profession.setText(prf);

                    }
                });
            }
        });setProfileInfo.start();





        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        class ViewPagerAdapter extends FragmentPagerAdapter{
            private ArrayList<Fragment>fragments;
            private  ArrayList<String> titles;


            ViewPagerAdapter(FragmentManager fragmentManager){
                super(fragmentManager);
                this.fragments=new ArrayList<>();
                this.titles=new ArrayList<>();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            public  void addFragment(Fragment fragment,String title){
                fragments.add(fragment);
                titles.add(title);
            }

           // @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        }
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BiographyFragment(),"Biography");
        viewPagerAdapter.addFragment(new PostsFragment(),"Posts");
        viewPagerAdapter.addFragment(new PartnersFragment(),"Partners");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);







    }





}
