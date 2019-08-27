package com.ducklings_corp.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MainActivity extends Activity {
    final String JSON_IMGS_STRING = "{\"Fotos\":[{\"Titulo\":\"Paisaje\",\"Ubicacion\":\"https://concepto.de/wp-content/uploads/2015/03/paisaje-e1549600034372.jpg\"}, {\"Titulo\":\"Personas\",\"Ubicacion\":\"https://www.importancia.org/wp-content/uploads/personas.gif\"},{\"Titulo\":\"Lenguajes de programacion\",\"Ubicacion\":\"http://imagenes.universia.net/gc/net/images/ciencia-tecnologia/l/le/len/lenguajes-de-programacion.jpg\"}, {\"Titulo\":\"Redes sociales\",\"Ubicacion\":\"https://www.redeszone.net/app/uploads/2019/05/perfil-seguridad-redes-sociales.jpg\"}, {\"Titulo\":\"Banderas\",\"Ubicacion\":\"https://previews.123rf.com/images/kadirtinte/kadirtinte1403/kadirtinte140300004/27564296-banderas-oficiales-de-los-pa%C3%ADses.jpg\"},{\"Titulo\":\"Autos\",\"Ubicacion\":\"https://www.infobae.com/new-resizer/HxTcqZ5ZrR_qP92CKrDA0Jd6hug=/750x0/filters:quality(100)/s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2019/01/23124051/los-autos-mas-vendidos-de-latam-de-2018-1920-.jpg\"}, {\"Titulo\":\"Peliculas\",\"Ubicacion\":\"https://i.blogs.es/25ffa9/pixar-30/450_1000.jpg\"}]}";
    ArrayList<Image> images = new ArrayList<>();

    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setImages(parseJson(JSON_IMGS_STRING));
    }
    private ArrayList<Image> parseJson(String json) {
        ArrayList<Image> images = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(json).getAsJsonObject();
        JsonArray imagesJsonArray = root.get("Fotos").getAsJsonArray();
        for(JsonElement image: imagesJsonArray) {
            JsonObject object = image.getAsJsonObject();
            Log.d("json",object.get("Titulo").getAsString());
            images.add(
                new Image(
                    object.get("Ubicacion").getAsString(),
                        object.get("Titulo").getAsString()
                )
            );
        }
        return images;
    }

    public void radioActionUpdate(View view) {
        switch (view.getId()) {
            case R.id.RadioView:
                createFragment(R.id.mainFrame,new FragmentViewImage(),"view");
                break;
            case R.id.RadioNew:
                createFragment(R.id.mainFrame,new FragmentAddImage(),"new");
                break;
            default:
                Log.d("Error","Invalid");
        }
    }
    private void createFragment(int id, Fragment fragment, String tag) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(id,fragment,tag);
        transaction.commit();
    }

    public ArrayList<Image> getImages() {
        return this.images;
    }
    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}

