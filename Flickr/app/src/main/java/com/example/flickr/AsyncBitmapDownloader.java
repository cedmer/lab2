package com.example.flickr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) { //Perform the HTTP connection, and reinstantiate the JSON object
        URL url = null;
        HttpURLConnection urlConnection = null;
        Bitmap bm = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); //Open the connection using the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //Get the content of the urlConnection
            bm = BitmapFactory.decodeStream(in); //decode the input stream to a bitmap format
            in.close(); //Close the input stream so we free resources
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {   //the code inside will be done anyway
            if (urlConnection != null)
                urlConnection.disconnect(); //Close the urlConnection
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("CIO", "Image received !");
        MainActivity.bm = bitmap; // set the public bitmap value locate in the main activity
    }

}
