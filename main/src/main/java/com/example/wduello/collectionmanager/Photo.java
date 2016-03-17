package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

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
