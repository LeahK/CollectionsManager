package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Item implements Serializable {

    private String mName;
    private HashMap<String, Attribute> mAttributes;
    private Photo mPhoto;

    public Item(String name) {
        mName = name;
        mAttributes = new HashMap<String, Attribute>();
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public HashMap<String, Attribute> getAttributes() {
        return mAttributes;
    }

    public void setAttributes(HashMap<String, Attribute> mAttributes) {
        this.mAttributes = mAttributes;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }


}
