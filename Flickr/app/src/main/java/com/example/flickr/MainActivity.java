package com.example.flickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    public static Bitmap bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnimage = findViewById(R.id.btnImage);
        Button btnlist = findViewById(R.id.btnlist);




        btnimage.setOnClickListener(new GetImageOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                setRes(bm);
            }
        });

        btnlist.setOnClickListener(new GetImageOnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

    }
    public void setRes(Bitmap bitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = findViewById(R.id.image_android);
                imageView.setImageBitmap(bitmap);
            }
        });
    }


}


