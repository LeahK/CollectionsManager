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


    public String getPhotoFilePath() {
        return mPhotoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) { mPhotoFilePath = photoFilePath; }

}
