package com.example.wduello.collectionmanager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ItemUnitTest {

    @Test
    public void item_newItem() throws Exception {
        Collection c = new Collection("Test Collection");
        Item i = new Item("Test Name", "Test Photo Path", c);
        assertTrue(i.getItemName().equals("Test Name"));
        assertTrue(i.getPhotoPath().equals("Test Photo Path"));
        assertTrue(i.getItemCollectionName().equals("Test Collection"));
    }

}