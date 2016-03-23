package com.example.wduello.collectionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Collection implements Serializable {

    private HashMap<String, Item> mItems;

    public Collection() {
        mItems = new HashMap<String, Item>();
    }

    public HashMap<String, Item> getItems() {
        return mItems;
    }

    public void setItems(HashMap<String, Item> mItems) {
        this.mItems = mItems;
    }


}
