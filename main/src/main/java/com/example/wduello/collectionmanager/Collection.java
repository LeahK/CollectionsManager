package com.example.wduello.collectionmanager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kayewrobleski on 3/6/16.
 */
public class Collection implements Serializable {

    private HashMap<String, Item> mItems;
    protected String mCollectionName;

    protected Collection(){}

    public Collection(String name) {
        mCollectionName = name;
        mItems = new HashMap<String, Item>();
    }

    private void listenForItemChanges(){

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
                    if (!mItems.containsKey(item.getItemName())){
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

    public HashMap<String, Item> getItems() {
        return mItems;
    }

    public void setItems(HashMap<String, Item> mItems) {
        this.mItems = mItems;
    }

    public String getCollectionName() {
        return mCollectionName;
    }

    public void setCollectionName(String mCollectionName) {
        this.mCollectionName = mCollectionName;
    }


    public void saveCollection() {

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
