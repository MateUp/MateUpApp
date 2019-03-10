package com.example.mateup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText EmailRegister;
    private EditText LastNameRegister;
    private EditText FirstNameRegister;
    private EditText CountryRegister;
    private EditText passwordRegister;
    private EditText RCH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        RegisterButton=(Button) findViewById(R.id.RegisterButton);
        EmailRegister=(EditText) findViewById(R.id.EmailRegister);
        passwordRegister = (EditText) findViewById(R.id.PasswordRegister);
        LastNameRegister=(EditText) findViewById(R.id.LastNameRegister);
        FirstNameRegister=(EditText) findViewById(R.id.FirstNameRegister);
        CountryRegister=(EditText) findViewById(R.id.CountryRegister);
        RCH = (EditText) findViewById(R.id.RCH);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoryLineActivity();
            }
        });

    }
    public void openStoryLineActivity(){
        Intent openStoryLine = new Intent(this, MainActivity.class);
        startActivity(openStoryLine);
    }
}