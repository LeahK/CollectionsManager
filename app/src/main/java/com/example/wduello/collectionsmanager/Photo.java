package com.example.wduello.collectionsmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by kayewrobleski on 3/7/16.
 */
public class Photo {

    private static final String JSON_PHOTO = "photo";

    private String mPhotoFilePath;

    public Photo(String photoFilePath) {
        mPhotoFilePath = photoFilePath;
    }

    public Photo(JSONObject json) throws JSONException {
        String jsonString = json.toString();
        mPhotoFilePath = jsonString;
    }

    public JSONObject toJSON()  throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_PHOTO, mPhotoFilePath);
        return json;
    }

    public String getPhotoFilePath() {
        return mPhotoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) { mPhotoFilePath = photoFilePath; }

}
