package com.example.mateup.FragmentsCardsForProfile;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mateup.LoginActivity;
import com.example.mateup.ProfileActivity;
import com.example.mateup.R;





public class BiographyFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biography, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        TextView userName = (TextView) view.findViewById(R.id.bio_user_name);
        TextView profession = (TextView) view.findViewById(R.id.bio_profession);
        TextView country = (TextView) view.findViewById(R.id.country);
        Context applicationContext = LoginActivity.getContextOfApplication();


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        final String firstName = pref.getString("firstName","not found");
        final String lastName = pref.getString("lastName","not found");
        final String prf = pref.getString("profession","not found");
        final String cntry = pref.getString("country","not found");

        userName.setText(firstName + " " + lastName);
        profession.setText(prf);
        country.setText(cntry);



    }





}









