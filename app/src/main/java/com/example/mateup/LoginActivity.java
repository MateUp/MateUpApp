package com.example.mateup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    public static Context contextOfApplication;


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

        contextOfApplication = getApplicationContext();



    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
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
                        Log.i("token", String.valueOf(res.get("token")));


                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("token", String.valueOf(res.get("token")));
                        editor.commit();

                        String token = sharedPref.getString("token","");
                        Log.i("shared",token);




                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            }

                        });

                        saveInfo();

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
        });t.start();


    }

    public void saveInfo()
    {
        try {
            RestClient rc = new RestClient("/users/auth");
            String response = rc.executeGet();
            Log.i("profil",response);

            JSONObject userInfo = new JSONObject(response);
            final String userName = userInfo.getString("firstName");
            final String lastName = userInfo.getString("lastName");
            final String prf = userInfo.getString("profession");
            final String cntry = userInfo.getString("country");

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("firstName", userName);
            editor.putString("lastName", lastName);
            editor.putString("profession", prf);
            editor.putString("country", cntry);
            editor.commit();


        }catch(Exception e)
        {
            Log.e("Filippp",e.getMessage());
            Log.e("Filippppp", e.toString());
            Log.v("Filipppp", "Verbos");
            e.printStackTrace();

        }
    }
}
