package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Advertisement implements Serializable {

    private static final String JSON_NAME = "name";
    private static final String JSON_TYPE = "type";
    private static final String JSON_VALUE = "value";

    private String mType;
    private String mName;
    private String mValue;

    public Advertisement(String type, String name, String value) {
        //Generate empty attribute
        mType = type;
        mName = name;
        mValue = value;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_TYPE, mType);
        json.put(JSON_NAME, mName);
        json.put(JSON_VALUE, mValue);
        return json;
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
