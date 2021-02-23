package com.example.flickr;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;

public class GetImageOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        task.execute();
    }
}
