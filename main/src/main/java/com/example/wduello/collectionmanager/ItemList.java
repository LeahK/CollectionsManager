package com.example.wduello.collectionmanager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/23/16.
 */
public class ItemList {

    private static final String TAG = "ItemList";
    private static final String FILENAME = "items.json";


    private ArrayList<LocalItem> mLocalItems;
    private CollectionsManagerJSONSerializer mSerializer;


    private static ItemList sItemList;
    private Context mAppContext;

    private ItemList(Context appContext) {
        mAppContext = appContext;
        mLocalItems = new ArrayList<LocalItem>();
        mSerializer = new CollectionsManagerJSONSerializer(mAppContext, FILENAME);

        try {
            mLocalItems = mSerializer.loadItems();
        } catch (Exception e) {
            mLocalItems = new ArrayList<>();
            Log.e(TAG, "Error loading items", e);
        }
    }

    public static ItemList get(Context c) {
        if (sItemList == null) {
            sItemList = new ItemList(c.getApplicationContext());
        }
        return sItemList;
    }

    public void addItem(LocalItem i) {
        mLocalItems.add(i);
    }

    public void deleteItem(LocalItem i ) { mLocalItems.remove(i); }

    public boolean saveItems() {
        try {
            mSerializer.saveItems(mLocalItems);
            Log.d(TAG, "items saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<LocalItem> getLocalItems() {
        return mLocalItems;
    }

    public LocalItem getItem(UUID id) {
        for (LocalItem i : mLocalItems) {
            if (i.getId().equals(id))
                return i;
        }
        return null;
    }

}
