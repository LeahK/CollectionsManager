package com.example.wduello.collectionsmanager;

import android.os.Parcel;
import android.os.Parcelable;

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
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_ATTRIBUTES = "attributes";
    private UUID mId;
    private int mCollectionId;
    private String mName;
    private Photo mPhoto;
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
        if (json.has(JSON_PHOTO)) {
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
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
        json.put(JSON_PHOTO, mPhoto.toJSON());
        ArrayList<JSONObject> jsonAttributes = new ArrayList<>();
        for (Attribute a : mAttributes) {
            jsonAttributes.add(a.toJSON());
        }
        json.put(JSON_ATTRIBUTES, jsonAttributes);
        return json;
    }

    public UUID getId() {
        return mId;
    }

    public int getCollectionId() {return mCollectionId; }

    public void setCollectionId(int collectionId) { mCollectionId = collectionId; }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Attribute> getAttributes() {
        //get attributes
        return mAttributes;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }

    public ArrayList<Attribute> getAttribute() {
        return mAttributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        mAttributes = attributes;
    }

    public void setAttributeValue(Attribute attribute, String value) {
        attribute.setValue(value);
    }

}
