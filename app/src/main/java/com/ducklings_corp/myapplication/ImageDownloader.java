package com.ducklings_corp.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    ImageDownloader(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap image = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection cnx;
            cnx = (HttpURLConnection) url.openConnection();
            if(cnx.getResponseCode()==200) {
                InputStream stream = cnx.getInputStream();
                BufferedInputStream buffer = new BufferedInputStream(stream);
                image = BitmapFactory.decodeStream(buffer);
                cnx.disconnect();
            } else {
                Log.d("image","Error: "+cnx.getResponseCode()+" / "+cnx.getResponseMessage());
            }
        } catch (Exception e) {
            Log.d("image",e.getMessage());
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap==null) {
            Log.d("image","Image download failed");
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
}