package com.example.wduello.collectionmanager;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Justin Gregorio
 * User model for creating, saving and manipulating user data
 */
public class User implements Serializable {

    private HashMap <String, Collection> mCollections;
    private String mEmail;
    private boolean mInstantiated = false;
    private String mUserName;

    // constructor
    public User(String email) {

        if (!mInstantiated) {
            mEmail = email;
            String[] emailParts = email.split("@");
            mUserName = emailParts[0];
            mCollections = new HashMap<String, Collection>();
            mInstantiated = true;
        }
        listenForCollectionChanges();

    }

    private void listenForCollectionChanges(){

        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef);
        collectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " collections");
                for (DataSnapshot collectionSnapshot : snapshot.getChildren()) {
                    Collection collection = collectionSnapshot.getValue(Collection.class);
                    if (!mCollections.containsKey(collection.getCollectionName())){
                        mCollections.put(collection.getCollectionName(), collection);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public String getEmail() {
        return mEmail;
    }

    public HashMap<String, Collection> getCollections() {
        return mCollections;
    }

    public void setCollections(HashMap<String, Collection> mCollections) {
        this.mCollections = mCollections;
    }

    public String getUserName() {
        return mUserName;
    }


    public void saveUser() {

        Firebase userRef = new Firebase("https://collectionsapp.firebaseio.com/users");
        Firebase currentUserRef = userRef.child(mUserName);
        currentUserRef.setValue(this, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("User data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("User data saved successfully.");
                }
            }
        });


    }



}
