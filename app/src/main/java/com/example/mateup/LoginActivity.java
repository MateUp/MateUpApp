package com.example.mateup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private EditText emailLogin, passwordLogin;
    private CheckBox rememberMe;
    private TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton=(Button) findViewById(R.id.loginButton);
        registerButton=(Button) findViewById(R.id.registerButton);
        emailLogin=(EditText) findViewById(R.id.emailLogin);
        passwordLogin=(EditText) findViewById(R.id.passwordLogin);
        rememberMe=(CheckBox) findViewById(R.id.rememberMe);
        textView2=(TextView) findViewById(R.id.textView2);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoryLineActivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }

        });

    }

    private void openRegisterActivity() {
        Intent openRegister = new Intent(this, RegisterActivity.class);
        startActivity(openRegister);
    }

    public void openStoryLineActivity(){
        final JSONObject user = new JSONObject();
        try {
            user.put("email", emailLogin.getText());
            user.put("password", passwordLogin.getText());


        } catch (JSONException e) {
            Log.e("Filippppp", "FILIP_ERR 1");
            e.printStackTrace();
        }


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


            }
        });
        t.start();
    }
}
