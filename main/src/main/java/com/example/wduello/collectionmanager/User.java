package com.example.wduello.collectionmanager;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin Gregorio
 * User model for creating, saving and manipulating user data
 */
public class User implements Serializable {

    private static Firebase userRef = new Firebase("https://collectionsapp.firebaseio.com/users");

    private HashMap <String, Collection> mCollections;
    private String mEmail;
    private String mPassword;
    private User mCurrentUser;

    // constructor
    public User(String userName, String password) {

        if (mCurrentUser == null) {
            mEmail = userName;
            mPassword = password;
            mCurrentUser = this;
            mCollections = new HashMap<String, Collection>();
        }

    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void saveUser() {

        Firebase currentUserRef = this.userRef.child(this.mEmail);
        currentUserRef.setValue(mCurrentUser, new Firebase.CompletionListener() {
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
