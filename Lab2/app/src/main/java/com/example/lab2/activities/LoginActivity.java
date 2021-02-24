package com.example.lab2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab2.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText textFieldLogin;
    private TextInputEditText textFieldPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textFieldLogin = findViewById(R.id.TextInputLogin);
        textFieldPassword = findViewById(R.id.TextInputPassword);
        Button btnAuthenticate = (Button) findViewById(R.id.btnAuthenticate);
        TextView result = findViewById(R.id.textviewresult);


        btnAuthenticate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                 new Thread(){
                     @Override
                     public void run(){

                         URL url = null;
                         try {
                             url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//Open the connection using the url
                             String infos = textFieldLogin.getText().toString()+":"+textFieldPassword.getText().toString();//add to string the input of the user
                             String basicAuth = "Basic " + Base64.encodeToString(infos.getBytes(),
                                     Base64.NO_WRAP);//convert to base 64 the string
                             urlConnection.setRequestProperty ("Authorization", basicAuth);

                             try {
                                 InputStream in = new BufferedInputStream(urlConnection.getInputStream());//Get the content of the urlConnection
                                 String s = readStream(in);
                                 Log.i("JFL", s);
                                 setRes(s); //Once we get the image in the bitmap, we display it
                             } finally {//the code inside will be done anyway
                                 urlConnection.disconnect();//Close the urlConnection
                             }
                         } catch (MalformedURLException e) {
                             e.printStackTrace();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }

                 }.start();


            }

        });
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public void setRes(String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView result = findViewById(R.id.textviewresult);
                result.setText(s);
            }
        });
    }
}