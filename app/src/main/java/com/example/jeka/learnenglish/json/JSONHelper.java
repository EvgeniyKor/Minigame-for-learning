package com.example.jeka.learnenglish.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.jeka.learnenglish.data.Doublet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "ru-en250.json";
    public static List<String> FILE_NAMES = Arrays.asList("ru-en250.json", "ru-en500.json", "ru-en750.json", "ru-en1000.json");
    private static Context _context;

    public JSONHelper(Context context) {
        _context = context;
    }

    public List<Doublet> importFromJSON(int fileNum)  {

            String json = AssetJSONFile(fileNum);
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<Doublet> doublets = new ArrayList<>();
            Log.e("json", ""+jsonArray.length());
            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Doublet doublet = new Doublet();
                doublet.setText(jsonObj.getString("text"));
                doublet.setAnswer(jsonObj.getString("answer"));
                doublets.add(doublet);
            }
            return doublets;

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String AssetJSONFile(int i)  {
        AssetManager manager = null;
        InputStream file = null;
        try{
            manager = _context.getAssets();
            file = manager.open(FILE_NAMES.get(i));
            byte[] formArray = new byte[file.available()];
            file.read(formArray);
            file.close();

            return new String(formArray);
        } catch (IOException e) {
                e.getStackTrace();
        }
        return null;
    }
}
