package com.example.wduello.collectionmanager;

import com.firebase.client.Firebase;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SaveCollectionUnitTest {

    private boolean mExists;

    @Test
    public void user_hasCollection_returnsTrue() throws Exception {
        mExists = false;
        Firebase ref = new Firebase("https://collectionsapp.firebaseio.com/users/kaye/collections");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mExists = dataSnapshot.hasChild("Collection1");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing
            }
        });
        assertTrue(mExists);
    }

    @Test
    public void user_hasCollection_returnsFalse() throws Exception {
        mExists = false;
        Firebase ref = new Firebase("https://collectionsapp.firebaseio.com/users/kaye/collections");
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