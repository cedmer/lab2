package com.example.flickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ListActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView list = findViewById(R.id.list);
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        AsyncFlickrJSONDataForList task = new AsyncFlickrJSONDataForList(adapter,"https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        task.execute();

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lastLocation = location;
                            Log.i("LOCATION TEST", "location :  " + lastLocation);

                        }
                    }
                });


    }
}