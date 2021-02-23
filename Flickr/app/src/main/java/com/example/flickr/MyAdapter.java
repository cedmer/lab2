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
        // ConvertView qui permet d'afficher tous les urls sur l'écran
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.textviewlayout, parent, false);
//            ((TextView)convertView.findViewById(R.id.textView2)).setText(vector.get(position));
//        }
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.bitmap_layout, parent, false);
        }

        // Cré&ation de l'imageView afin de pouvoir l'utiliser dans la lambda expression. Si instanciée dans la lambda exp, ça ne fonctionne pas.
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);

        // Création d'un bitmap pour permettre de charger l'image
        Response.Listener<Bitmap> rep_listener = response -> {
            img.setImageBitmap(response);
        };

        // On crée une imageRequest contenant la position de l'url de l'image dasn le vector et une création de bitmap avec els response listener
        ImageRequest request = new ImageRequest(vector.get(position), rep_listener,  0, 0, ImageView.ScaleType.CENTER_CROP, null, null);


        // On ajoute au singleton (qui est un genre de liste chainée) les information de l'image que l'oin cherche à charger.
        MySingleton.getInstance(parent.getContext()).addToRequestQueue(request);

        return convertView;
    }

        public  void dd(String url){
        vector.add(url);
        Log.i("JFL", "Adding to adapter url : " + url);
    }
}
