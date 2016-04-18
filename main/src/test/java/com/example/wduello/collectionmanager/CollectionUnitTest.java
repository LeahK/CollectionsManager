package com.example.wduello.collectionmanager;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CollectionUnitTest {

    private Collection mCollection;
    private HashMap<String, Item> mItems;
    private Item mItem;

    @Test
    public void collection_hasItem() throws Exception {
        mCollection = new Collection("Test Collection");
        mItem = new Item("Test Item", null, mCollection);
        mItems = new HashMap<>();
        mItems.put(mItem.getItemName(), mItem);
        mCollection.setItems(mItems);
        assertTrue(mCollection.getItems().containsKey(mItem.getItemName()));
    }

    @Test
    public void collection_itemRemoved() throws Exception {
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