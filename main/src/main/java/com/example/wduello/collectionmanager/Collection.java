package com.example.wduello.collectionmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Collection implements Serializable {

    private HashMap<String, Item> mItems;
    protected String mCollectionName;
    private String mPhotoPath;
    private UUID mCollectionId;

    // empty constructor for deserialization
    protected Collection(){

    }

    public Collection(String name) {
        mCollectionName = name;
        mPhotoPath = null;
        mItems = new HashMap<String, Item>();
        mCollectionId = UUID.randomUUID();
    }

    public void listenForItemChanges(){

        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef);
        Firebase itemRef = collectionRef.child(mCollectionName);
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " collections");
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    if (!mItems.containsKey(item.getItemName())) {
                        mItems.put(item.getItemName(), item);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public UUID getCollectionId() {
        return mCollectionId;
    }

    public void setCollectionId(UUID collectionId) { mCollectionId = collectionId; }

    public HashMap<String, Item> getItems() {
        return mItems;
    }

    @JsonIgnore
    public ArrayList<Item> getItemsArrayList() {
        ArrayList<Item> items = new ArrayList<>();
        if (mItems != null) {
            items = new ArrayList<>(mItems.values());
        }
        return items;
    }

    public void setItems(HashMap<String, Item> mItems) {
        this.mItems = mItems;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String photoPath ) {
        this.mPhotoPath = photoPath;
    }

    public String getCollectionName() {
        return mCollectionName;
    }

    public void setCollectionName(String mCollectionName) {
        this.mCollectionName = mCollectionName;
    }

    /*
    *   Adds this Collection to the current User's list of collections and saves it in
    *   the database.
     */
    public void saveCollection() {

        // add to local collections
        HashMap<String, Collection> collections = ActivityLogin.mCurrentUser.getCollections();
        collections.put(this.mCollectionName, this);
        ActivityLogin.mCurrentUser.setCollections(collections);

        // save the collection in the database
        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef).child(mCollectionName);
        collectionRef.setValue(this, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Collection data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Collection data saved successfully.");
                }
            }
        });


    }

}
