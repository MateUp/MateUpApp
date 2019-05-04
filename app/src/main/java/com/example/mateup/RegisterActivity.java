package com.example.mateup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mateup.services.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText EmailRegister;
    private EditText LastNameRegister;
    private EditText FirstNameRegister;
    private EditText CountryRegister;
    private EditText passwordRegister;
    private EditText RCH;
    ProgressDialog progressDialog;


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
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Please Wait");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Reggistering...");
                progressDialog.show();
                openLoginActivity();
            }
        });



    }
    public void openLoginActivity(){




        final JSONObject user = new JSONObject();
        try {
            user.put("email", EmailRegister.getText());
            user.put("firstName", FirstNameRegister.getText());
            user.put("lastName", LastNameRegister.getText());
            user.put("password", passwordRegister.getText());
            user.put("country",CountryRegister.getText());
            user.put("profession", RCH.getText());
        } catch (JSONException e) {
            Log.e("Filippppp", "FILIP_ERR 1");
            e.printStackTrace();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        RestClient rc = new RestClient("/users");
                        String response = rc.executePost(user.toString());




                    Log.i("Server response", response);
                    if (response != null) {
                        JSONObject res = new JSONObject(response);
                        res.get("token");

                        Intent openLoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                        progressDialog.dismiss();
                        startActivity(openLoginActivity);





                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),"registered",Toast.LENGTH_LONG).show();
                            }
                        });









                    }

                } catch (Exception e) {
                    Log.e("Filippppp", e.getMessage());
                    Log.e("Filippppp", e.toString());
                    Log.v("Filipppp", "Verbos");
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        t.start();




    }


}