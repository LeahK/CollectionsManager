package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Attribute implements Serializable {

    private String mType;
    private String mName;
    private String mValue;

    public Attribute(String type, String name, String value) {
        mType = type;
        mName = name;
        mValue = value;
    }



    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
