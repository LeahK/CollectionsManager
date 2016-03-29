package com.example.wduello.collectionmanager;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/28/16.
 */
public class CollectionList {

    private static final String TAG = "CollectionList";

    private ArrayList<Collection> mCollections;
    private Context mAppContext;

    private static CollectionList sCollectionList;

    private CollectionList(Context appContext) {
        mAppContext = appContext;
        mCollections = new ArrayList<Collection>();

    }

    public static CollectionList get(Context c) {
        if (sCollectionList == null) {
            sCollectionList = new CollectionList(c.getApplicationContext());
        }
        return sCollectionList;
    }

    public void addCollection(Collection c) {
        mCollections.add(c);
    }

    public void deleteCollection(Collection c) { mCollections.remove(c); }

    public ArrayList<Collection> getCollections() {
        return mCollections;
    }

    public Collection getCollection(UUID id) {
        for (Collection c : mCollections) {
            if (c.getCollectionId().equals(id))
                return c;
        }
        return null;
    }
}
