package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Attribute implements Serializable {

    private String mType;
    private String mValue;

    public Attribute(String type, String value) {
        mType = type;
        mValue = value;
    }



    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
