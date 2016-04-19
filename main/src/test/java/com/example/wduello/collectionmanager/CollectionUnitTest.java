package com.example.wduello.collectionmanager;

import org.junit.Test;

import java.util.HashMap;

import dalvik.annotation.TestTarget;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CollectionUnitTest {

    private Collection mCollection;
    private HashMap<String, Item> mItems;
    private Item mItem;

    @Test
    public void collection_getCollectionName() throws Exception{
        mCollection = new Collection("Test Collection");
        assertTrue(mCollection.getCollectionName().equals("Test Collection"));
    }

    @Test
    public void collection_setCollectionName() throws Exception{
        mCollection = new Collection("Test Collection");
        mCollection.setCollectionName("Test Collection 2");
        assertTrue(mCollection.getCollectionName().equals("Test Collection 2"));
    }

    @Test
    public void collection_setPhotoPath() throws Exception{
        mCollection = new Collection("Test Collection");
        mCollection.setPhotoPath("Test Photo Path");
        assertTrue(mCollection.getPhotoPath().equals("Test Photo Path"));
    }

    @Test
    public void collection_addItem() throws Exception {
        mCollection = new Collection("Test Collection");
        mItem = new Item("Test Item", null, mCollection);
        mItems = new HashMap<>();
        mItems.put(mItem.getItemName(), mItem);
        mCollection.setItems(mItems);
        assertTrue(mCollection.getItems().containsKey(mItem.getItemName()));
    }

    @Test
    public void collection_removeItem() throws Exception {
        mCollection = new Collection("Test Collection");
        mItem = new Item("Test Item", null, mCollection);
        mItems = new HashMap<>();
        mItems.put(mItem.getItemName(), mItem);
        mCollection.setItems(mItems);
        mItems.remove(mItem.getItemName());
        mCollection.setItems(mItems);
        assertFalse(mCollection.getItems().containsKey(mItem.getItemName()));
    }
}