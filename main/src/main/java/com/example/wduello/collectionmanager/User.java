package com.example.wduello.collectionmanager;


import com.firebase.client.Firebase;

import java.io.Serializable;

/**
 * Created by Justin Gregorio
 * User model for creating, saving and manipulating user data
 */
public class User implements Serializable {

    private static Firebase userRef = new Firebase("https://collectionsapp.firebaseio.com/users");
    private String mUserName;
    private String mPassword;

    // constructor
    public User(String userName, String password) {

        mUserName = userName;
        mPassword = password;

    }




}
