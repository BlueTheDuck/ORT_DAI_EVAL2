package com.ducklings_corp.myapplication;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class FragmentAddImage extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private View view;
    private ArrayList<Image> images;
    private ImageDownloader previewer;

    private EditText imageTitle;
    private EditText imageUrl;
    private ImageView imageView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.add_image_fragment,viewGroup,false);

        images = ((MainActivity)getActivity()).getImages();

        imageTitle = view.findViewById(R.id.imageTitle);
        imageUrl = view.findViewById(R.id.imageUrl);
        imageView = view.findViewById(R.id.imageView);

        previewer = new ImageDownloader(imageView);

        view.findViewById(R.id.submit).setOnClickListener(this);
        view.findViewById(R.id.imageUrl).setOnFocusChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String imageTitleString = imageTitle.getText().toString();
        String imageUrlString = imageUrl.getText().toString();
        if(imageTitleString.length()==0) {
            imageTitle.setError("Ingrese un titulo");
        }
        if(imageUrlString.length()==0) {
            imageUrl.setError("Ingrese una URL");
        }
        if(imageTitleString.length()!=0 && imageUrlString.length()!=0) {
            images.add(new Image(
                imageUrlString,
                imageTitleString
            ));
            imageTitle.setText("");
            imageUrl.setText("");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            if(previewer.getStatus()==AsyncTask.Status.RUNNING) {
                previewer.cancel(true);
            }
        } else {
            previewer = new ImageDownloader(imageView);
            previewer.execute(imageUrl.getText().toString());
        }
    }
}
