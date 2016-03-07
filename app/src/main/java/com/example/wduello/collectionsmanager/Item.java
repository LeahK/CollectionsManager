package com.example.wduello.collectionsmanager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Item {
    private UUID mId;
    private int mCollectionId;
    private String mName;
    private ArrayList<Attribute> mAttributes;

    public Item(String name, int collectionId) {
        //Generate item with name
        mId = UUID.randomUUID();
        mCollectionId = collectionId;
        mName = name;
        mAttributes = new ArrayList<>();
    }

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
