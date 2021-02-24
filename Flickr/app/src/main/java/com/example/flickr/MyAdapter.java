package com.example.flickr;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    private ListView list;
    private Vector<String> vector;
    public MyAdapter() {
        vector = new Vector<String>();
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ConvertView which allows to display all the urls on the screen
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.textviewlayout, parent, false);
//            ((TextView)convertView.findViewById(R.id.textView2)).setText(vector.get(position));
//        }
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.bitmap_layout, parent, false);
        }

        //Creation of the imageView so that it can be used in the lambda expression. If instantiated in lambda exp, it doesn't work.
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);

        // Create a bitmap to load the image
        Response.Listener<Bitmap> rep_listener = response -> {
            img.setImageBitmap(response);
        };


        // We create an imageRequest containing the position of the image url in the vector and a bitmap creation with the response listener
        ImageRequest request = new ImageRequest(vector.get(position), rep_listener,  0, 0, ImageView.ScaleType.CENTER_CROP, null, null);

        // We add to the singleton (which is a kind of chain list) the information of the image that we are trying to load.
        MySingleton.getInstance(parent.getContext()).addToRequestQueue(request);

        return convertView;
    }

        public  void dd(String url){
        vector.add(url);
        Log.i("JFL", "Adding to adapter url : " + url);
    }
}
