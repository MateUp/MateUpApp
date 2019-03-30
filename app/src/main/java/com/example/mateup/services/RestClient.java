package com.example.mateup.services;

/*Copyright 2014 Bhavit Singh Sengar
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mateup.LoginActivity;
import com.example.mateup.RegisterActivity;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RestClient {


    JSONObject data = new JSONObject();
    String url;
    String headerName;
    String headerValue;



    Context applicationContext = LoginActivity.getContextOfApplication();
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    String token = pref.getString("token","not found");


    Header header2 = new BasicHeader(HttpHeaders.AUTHORIZATION, token);
    Header header1 = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");



    public RestClient(String s) {


        url = "https://mateup.nstechlabs.com/api" + s;

    }




    public void addHeader(String name, String value) {

        headerName = name;
        headerValue = value;

    }

    public void addParam(String key, String value) {

        try {
            data.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public String executePost(String data) {  // If you want to use post method to hit server

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse response = null;
        String result = null;
        try {
            Log.i("Filip", this.url);
            StringEntity entity = new StringEntity(data, HTTP.UTF_8);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
            return result;
            //Toast.makeText(MainPage.this, result, Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Filip", "E1");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Filip", "E2");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Filip", e.getMessage());
        }
        return result;


    }

    public String executeGet() { //If you want to use get method to hit server

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpget.setHeader(HttpHeaders.AUTHORIZATION,token);
        String result = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();


        try {
            result = httpClient.execute(httpget, responseHandler);


        } catch (ClientProtocolException e) {

            e.printStackTrace();
            Log.e("get funkcija", e.getMessage());
            String response = e.getMessage();
            Log.i("pref",token);
            return response;


        } catch (IOException e) {

            e.printStackTrace();


        }


        return result;

    }


}


