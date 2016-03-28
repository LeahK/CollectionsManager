package com.example.wduello.collectionmanager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

/**
 *  Created by Justin Gregorio.
 */
public class Item extends Collection implements Serializable {

    private HashMap<String, Attribute> mAttributes;
    private String mPhotoPath;
    protected String mItemName;

    protected  Item(){}

    public Item(String name, String photoPath) {
        mAttributes = new HashMap<String, Attribute>();
        mItemName = name;
        mPhotoPath = photoPath;
        listenForAttributeChanges();
    }

    private void listenForAttributeChanges(){

        String attributeRefUrl = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/" + mCollectionName + "/" + mItemName + "/attributes/";
        Firebase attributeRef = new Firebase(attributeRefUrl);
        attributeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " collections");
                for (DataSnapshot attributeSnapshot : snapshot.getChildren()) {
                    Attribute attribute = attributeSnapshot.getValue(Attribute.class);
                    if (!mAttributes.containsKey(attribute.getAttributeName())){
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

    public void saveItem() {

        String userCollectionRef = "https://collectionsapp.firebaseio.com/users/"
                + ActivityLogin.mCurrentUser.getUserName() + "/collections/";
        Firebase collectionRef = new Firebase(userCollectionRef);
        Firebase itemRef = collectionRef.child(super.mCollectionName).child(mItemName);
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
}
