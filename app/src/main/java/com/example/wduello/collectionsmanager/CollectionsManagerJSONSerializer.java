package com.example.wduello.collectionsmanager;

/**
 * Created by kayewrobleski on 3/6/16.
 */

import android.content.Context;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.OutputStream;


public class CollectionsManagerJSONSerializer {

    private Context mContext;
    private String mFilename;

    public CollectionsManagerJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveItem(Item item) throws JSONException, IOException {
        //Build an object in JSON
        JSONObject object = item.toJSON();

        //Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer.write(object.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public Item loadItem() throws IOException, JSONException {
        Item item = new Item();
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
            JSONObject object = new JSONObject(jsonString.toString());
            item = new Item(object);
        } catch (FileNotFoundException e) {
            //Ignore
        } finally {
            if (reader != null)
                reader.close();
        }
        return item;
    }
}
