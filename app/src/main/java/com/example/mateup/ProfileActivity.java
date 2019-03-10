package com.example.mateup;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mateup.FragmentsCardsForProfile.BiographyFragment;
import com.example.mateup.FragmentsCardsForProfile.PartnersFragment;
import com.example.mateup.FragmentsCardsForProfile.PostsFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView name_of_user, profession;
    private CircleImageView new_profile_image;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name_of_user=(TextView) findViewById(R.id.name_of_user);
        profession=(TextView) findViewById(R.id.profession);
        new_profile_image=(CircleImageView) findViewById(R.id.new_profileImage);



        TabLayout tabLayout=findViewById(R.id.tab_layout);
         ViewPager viewPager=findViewById(R.id.view_pager);



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
