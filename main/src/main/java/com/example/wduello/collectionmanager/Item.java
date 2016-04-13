package com.example.wduello.collectionmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *  Created by Justin Gregorio.
 */
public class Item extends Collection implements Serializable {

    private UUID mId;
    private HashMap<String, Attribute> mAttributes;
    private String mPhotoPath;
    protected String mItemName;
    private boolean mAdvertised;

    // empty constructor for deserialization
    protected  Item() {
    }

    public Item(String name, String photoPath, Collection collection) {
        mAttributes = new HashMap<String, Attribute>();
        mItemName = name;
        mPhotoPath = photoPath;
        mId = UUID.randomUUID();
    }

    public void listenForAttributeChanges(){

        String attributeRefUrl = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/" + mCollectionName + "/" + mItemName + "/attributes/";
        Firebase attributeRef = new Firebase(attributeRefUrl);
        attributeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " collections");
                for (DataSnapshot attributeSnapshot : snapshot.getChildren()) {
                    Attribute attribute = attributeSnapshot.getValue(Attribute.class);
                    if (!mAttributes.containsKey(attribute.getAttributeName())) {
                        mAttributes.put(attribute.getAttributeName(), attribute);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public HashMap<String, Attribute> getAttributes() {
        return mAttributes;
    }

    public ArrayList<Attribute> getAttributesArrayList() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        if (mAttributes != null){
            attributes = new ArrayList<>(mAttributes.values());
        }
        return attributes;
    }

    public void setAttributes(HashMap<String, Attribute> mAttributes) {
        this.mAttributes = mAttributes;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String photoPath ) {
        this.mPhotoPath = photoPath;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String mName) {
        this.mItemName = mName;
    }

    public boolean isAdvertised() {
        return mAdvertised;
    }

    public void setAdvertised(boolean advertised) {
        mAdvertised = advertised;
    }

    public UUID getId() {
        return mId;
    }

    /*
    *   Saves this Item to local storage and the database.
     */
    public void saveItem() {
        // save to local
        Collection myCollection = ActivityLogin.mCurrentUser.getCollection(mCollectionName);
        HashMap<String, Item> items = myCollection.getItems();
        items.put(mItemName, this);
        myCollection.setItems(items);

        // save to database
        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef);
        Firebase itemRef = collectionRef.child(mCollectionName).child(mItemName);
        itemRef.setValue(this, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Item data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Item data saved successfully.");
                }
            }
        });
    }

    /*

    //Convert LocalItem object to JSON object
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_COLLECTION_ID, ""+mCollectionId);
        json.put(JSON_NAME, mName);
        json.put(JSON_PHOTO, mPhotoPath);
        json.put(JSON_ADVERTISED, mAdvertised);
        //Attributes added in future functionality
        /*
        ArrayList<JSONObject> jsonAttributes = new ArrayList<>();
        for (Attribute a : mAttributes) {
            jsonAttributes.add(a.toJSON());
        }
        json.put(JSON_ATTRIBUTES, jsonAttributes);

        return json;
    }

    //Create LocalItem object from JSON object
    public LocalItem(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mCollectionId = json.getInt(JSON_COLLECTION_ID);
        if (json.has(JSON_NAME)) {
            mName = json.getString(JSON_NAME);
        }
        mAdvertised = json.getBoolean(JSON_ADVERTISED);
        if (json.has(JSON_PHOTO)) {
            mPhotoPath = json.getString(JSON_PHOTO);
        }
        //Attributes added in future functionality
        /*
        String [] attributes = json.getString(JSON_ATTRIBUTES).split(";");
        for (String a: attributes) {
            String[] attribute = a.split(",");
            Attribute newAttribute = new Attribute(attribute[0], attribute[1], attribute[2]);
            mAttributes.add(newAttribute);
        }
        */
}
