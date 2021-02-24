package com.example.flickr;

import android.os.AsyncTask;
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


public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myActivity;
    private MyAdapter myadapter;
    private String httpUrl;

    public AsyncFlickrJSONDataForList(MyAdapter adapter, String httpUrl) {
        this.myadapter = adapter;
        this.httpUrl = httpUrl;
    }

    protected JSONObject doInBackground(String... strings) {
        URL url =null;
        JSONObject myJSONObject = null;
        try {
            url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//Open the connection using the url
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());//Get the content of the urlConnection
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

        return myJSONObject;
    }




    protected void onPostExecute(JSONObject s) {
        try {
            JSONArray items = s.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)//in order to get all the item
            {
                JSONObject flickr_entry = items.getJSONObject(i);//get the i item
                String urlmedia = flickr_entry.getJSONObject("media").getString("m");//get the item name : media
                Log.i("CIO", "URL media: " + urlmedia);

                myadapter.dd(urlmedia);// add it to Myadapter()

            }
            myadapter.notifyDataSetChanged();
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
            return bo.toString().replace("jsonFlickrFeed(","");// use replace to remove the begining
        } catch (IOException e) {
            return "";
        }
    }


}
