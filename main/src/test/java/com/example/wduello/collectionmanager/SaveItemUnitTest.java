package com.example.wduello.collectionmanager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SaveItemUnitTest {

    private boolean mExists;

    @Test
    public void collection_hasItem_returnsFalse() throws Exception {
        mExists = false;
        Firebase ref = new Firebase("https://collectionsapp.firebaseio.com/users/kaye/collections/Collection1/items");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mExists = dataSnapshot.hasChild("thisIsATest");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing
            }
        });
        assertFalse(mExists);
    }

}