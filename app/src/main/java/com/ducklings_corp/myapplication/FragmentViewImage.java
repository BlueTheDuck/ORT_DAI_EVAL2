package com.ducklings_corp.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentViewImage extends Fragment implements View.OnClickListener {
    private View view;
    private ArrayList<Image> images;
    private EditText imageIdInput;
    private ImageView imageView;
    private TextView imageTitle;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.view_image_fragment,viewGroup,false);

        images = ((MainActivity)getActivity()).getImages();

        imageIdInput = view.findViewById(R.id.imageId);
        imageIdInput.setHint("Rango [1;"+images.size()+"]");

        imageView = view.findViewById(R.id.imageView);
        imageTitle = view.findViewById(R.id.imageTitle);

        view.findViewById(R.id.displayImage).setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        String inputText = imageIdInput.getText().toString();
        try {
            int imageId = Integer.parseInt(inputText)-1;
            if(imageId>=0 && imageId < images.size()) {
                displayImage(imageId);
            } else {
                imageIdInput.setError("Ingrese un numero en [1;"+images.size()+"]");
            }
        }
        catch (NumberFormatException e) {
            imageIdInput.setError("Ingrese un numero en [1;"+images.size()+"]");
        }
    }

    void displayImage(int imageId) {
        Log.d("image","Displaying "+imageId);
        (new ImageDownloader(imageView)).execute(
            images.get(imageId).urlStr
        );
        imageTitle.setText(images.get(imageId).title);
    }
}

