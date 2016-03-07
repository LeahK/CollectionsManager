package com.example.wduello.collectionsmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Item {

    private static final String JSON_ID = "id";
    private static final String JSON_COLLECTION_ID = "collectionId";
    private static final String JSON_NAME = "name";
    private static final String JSON_ATTRIBUTES = "attributes";
    private UUID mId;
    private int mCollectionId;
    private String mName;
    private ArrayList<Attribute> mAttributes;

    public Item() {
        //Generate item with name
        mId = UUID.randomUUID();
    }

    public Item(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mCollectionId = json.getInt(JSON_COLLECTION_ID);
        if (json.has(JSON_NAME)) {
            mName = json.getString(JSON_NAME);
        }
        String [] attributes = json.getString(JSON_ATTRIBUTES).split(";");
        for (String a: attributes) {
            String[] attribute = a.split(",");
            Attribute newAttribute = new Attribute(attribute[0], attribute[1], attribute[2]);
            mAttributes.add(newAttribute);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_COLLECTION_ID, ""+mCollectionId);
        json.put(JSON_NAME, mName);
        String attributes = "";
        for (Attribute a : mAttributes) {
            String type = a.getType();
            String name = a.getName();
            String value = a.getValue();
            String attribute = type + "," + name + "," + value + ";";
            attributes = attributes + attribute;
        }
        json.put(JSON_ATTRIBUTES, attributes);
        return json;
    }

    public int getCollectionId() {return mCollectionId; }

    public void setCollectionId(int collectionId) { mCollectionId = collectionId; }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Attribute> getAttributes(int collectionId) {
        //get attributes for collectionid
        return mAttributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        mAttributes = attributes;
    }

    public void setAttributeValue(Attribute attribute, String value) {
        attribute.setValue(value);
    }
}
