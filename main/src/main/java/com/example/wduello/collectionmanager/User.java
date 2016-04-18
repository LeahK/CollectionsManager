package com.example.wduello.collectionmanager;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Justin Gregorio
 * User model for creating, saving and manipulating user data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private HashMap <String, Collection> mCollections;
    private String mEmail;
    private String mUserName;

    // constructor so deserialization will work
    private User(){}

    // constructor
    public User(String email) {

        mEmail = email;
        String[] emailParts = email.split("@");
        mUserName = emailParts[0];
        mCollections = new HashMap<String, Collection>();

    }

    public void listenForUserChanges(){
        String userRefUrl = "https://collectionsapp.firebaseio.com/users/";
        Firebase userRef = new Firebase(userRefUrl);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " users");
                HashMap<String, User> users = new HashMap<String, User>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.put(user.getUserName(), user);
                }
                if (users.containsKey(ActivityLogin.mCurrentUser.getUserName())) {
                    ActivityLogin.mCurrentUser = users.get(ActivityLogin.mCurrentUser.getUserName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void listenForCollectionChanges(){

        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + this.getUserName() + "/collections/";
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

    @JsonIgnore
    public ArrayList<Collection> getCollectionsArrayList(){
        ArrayList<Collection> collections = new ArrayList<>(mCollections.values());
        return collections;

    }

    public void setCollections(HashMap<String, Collection> mCollections) {
        this.mCollections = mCollections;
    }

    public Collection getCollectionByName(String colName){
        Collection searchedForCollection = null;

        if (mCollections.containsKey(colName)){
            searchedForCollection = this.mCollections.get(colName);
        }

        return searchedForCollection;
    }

    public String getUserName() {
        return mUserName;
    }

    public Collection getCollection(String collectionName){
        if (!mCollections.containsKey(collectionName)){
            return null;
        } else {
            return mCollections.get(collectionName);
        }
    }

    public void removeCollection(String collectionName){
        if (mCollections.containsKey(collectionName)){
            mCollections.remove(collectionName);
        }
    }


    public void saveUser() {

        Firebase userRef = new Firebase("https://collectionsapp.firebaseio.com/users" + "/" + mUserName + "/");

        userRef.setValue(this, new Firebase.CompletionListener() {
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
