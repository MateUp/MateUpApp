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
import android.widget.Toast;

import com.example.mateup.services.RestClient;

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

                try {
                    RestClient rc = new RestClient("/users/login");
                    String response = rc.executePost(user.toString());



                    if (response != null) {
                        JSONObject res = new JSONObject(response);
                        res.get("token");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            }

                        });

                        Intent openStoryLineActivity = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(openStoryLineActivity);


                    }


                }catch (Exception e)
                {
                    Log.e("Filippppp", e.getMessage());
                    Log.e("Filippppp", e.toString());
                    Log.v("Filipppp", "Verbos");
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    });


                }




            }
        });
        t.start();
    }
}
