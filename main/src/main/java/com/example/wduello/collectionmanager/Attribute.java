package com.example.wduello.collectionmanager;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;

/**
 * Created by Justin Gregorio
 */
public class Attribute extends Item implements Serializable {

    private String mType;
    private String mValue;
    private String mAttributeName;

    public Attribute(String name, String type, String value) {
        mType = type;
        mValue = value;
        mAttributeName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }


    public String getAttributeName() {
        return mAttributeName;
    }

    public void setmAttributeName(String mName) {
        this.mAttributeName = mName;
    }

    public void saveAttribute() {

        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef);
        Firebase attributeRef = collectionRef.child(mCollectionName).child(mItemName).child("attributes").child(mAttributeName);
        attributeRef.setValue(this, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Attribute data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Attribute data saved successfully.");
                }
            }
        });
    }

}
