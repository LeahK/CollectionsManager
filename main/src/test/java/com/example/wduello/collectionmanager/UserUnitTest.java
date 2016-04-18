package com.example.wduello.collectionmanager;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by kayewrobleski on 4/18/16.
 */
public class UserUnitTest {

    private User mUser;
    private Collection mCollection;
    private HashMap<String, Collection> mCollections;

    @Test
    public void user_hasCollection() {
        mUser = new User("test@test.com");
        mCollection = new Collection("Test Collection");
        mCollections = new HashMap<>();
        mCollections.put("Test Collection", mCollection);
        mUser.setCollections(mCollections);
        assertTrue(mUser.getCollections().containsKey("Test Collection"));

    }

    @Test
    public void user_collectionRemoved() {
        mUser = new User("test@test.com");
        mCollection = new Collection("Test Collection");
        mCollections = new HashMap<>();
        mCollections.put("Test Collection", mCollection);
        mUser.setCollections(mCollections);
        mCollections.remove("Test Collection");
        mUser.setCollections(mCollections);
        assertFalse(mUser.getCollections().containsKey("Test Collection"));

    }
}
