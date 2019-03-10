package com.example.mateup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText EmailRegister, PasswordRegister, LastNameRegister, FirstNameRegister;
    private EditText CountryRegister, RCH;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        RegisterButton=(Button) findViewById(R.id.RegisterButton);
        EmailRegister=(EditText) findViewById(R.id.EmailRegister);
        PasswordRegister=(EditText) findViewById(R.id.PasswordRegister);
        LastNameRegister=(EditText) findViewById(R.id.LastNameRegister);
        FirstNameRegister=(EditText) findViewById(R.id.FirstNameRegister);
        CountryRegister=(EditText) findViewById(R.id.CountryRegister);
        RCH=(EditText) findViewById(R.id.RCH);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

    }
    public void openProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}