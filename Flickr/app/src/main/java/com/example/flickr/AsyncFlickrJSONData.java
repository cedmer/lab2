package com.example.flickr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myActivity;

    public AsyncFlickrJSONData() {

    }

    protected JSONObject doInBackground(String... httpUrl) {
        URL url =null;
        JSONObject myJSONObject = null;
        for(int i=0; i<httpUrl.length;i++){ //to get all url
            try {
                url = new URL(httpUrl[i]); //get the i url
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//Open the connection using the url
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());  //Get the content of the urlConnection
                    String s = readStream(in);//Read this content as a string
                    Log.i("JFL", s);
                    myJSONObject = new JSONObject(s);//Create a JSON Object with the string we just translate
                    in.close(); //Close the input stream so we free resources
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return myJSONObject;
    }




        protected void onPostExecute(JSONObject s) {//Log the obtained JSON object in Logcat
            try {
                JSONArray items = null;
                try {
                    items = s.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    JSONObject flickr_entry = items.getJSONObject(0);//get the first item
                    String urlmedia = flickr_entry.getJSONObject("media").getString("m");//get the item name : media
                    Log.i("CIO", "URL media: " + urlmedia);

                    AsyncBitmapDownloader abd = new AsyncBitmapDownloader();//Prepare the bitmap downloader
                    abd.execute(urlmedia);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i;
            i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString().replace("jsonFlickrFeed(",""); // use replace to remove the begining
        } catch (IOException e) {
            return "";
        }
    }


}
