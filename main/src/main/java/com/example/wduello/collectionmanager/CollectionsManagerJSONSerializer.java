package com.example.wduello.collectionmanager;

/**
 * Created by kayewrobleski on 3/6/16.
 */

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.OutputStream;
import java.util.ArrayList;


public class CollectionsManagerJSONSerializer {

    private Context mContext;
    private String mFilename;

    public CollectionsManagerJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    /*
    public void saveItems(ArrayList<Item> localItems) throws JSONException, IOException {

        //Build an array in JSON
        JSONArray array = new JSONArray();

        for (Item i : localItems)
            array.put(i.toJSON());

        //Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    } */

    /*

    public ArrayList<Item> loadItems() throws IOException, JSONException {
        ArrayList<Item> localItems = new ArrayList<>();
        BufferedReader reader = null;
        try {
            //Open and read file into StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            //Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                localItems.add(new Item(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //Ignore
        } finally {
            if (reader != null)
                reader.close();
        }
        return localItems;
    }
    */
}
