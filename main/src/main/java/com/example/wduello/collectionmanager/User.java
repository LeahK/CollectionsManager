package com.example.wduello.collectionmanager;


import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

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
        currentUserRef.child("collections").setValue(this.mCollections, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });


    }



}
