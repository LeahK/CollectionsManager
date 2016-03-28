package com.example.wduello.collectionsmanager;

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

    private ArrayList<Item> mItems;
    private CollectionsManagerJSONSerializer mSerializer;


    private static ItemList sItemList;
    private Context mAppContext;

    private ItemList(Context appContext) {
        mAppContext = appContext;
        mItems = new ArrayList<Item>();
        mSerializer = new CollectionsManagerJSONSerializer(mAppContext, FILENAME);

        try {
            mItems = mSerializer.loadItems();
        } catch (Exception e) {
            mItems = new ArrayList<Item>();
            Log.e(TAG, "Error loading items", e);
        }
    }

    public static ItemList get(Context c) {
        if (sItemList == null) {
            sItemList = new ItemList(c.getApplicationContext());
        }
        return sItemList;
    }

    public void addItem(Item i) {
        mItems.add(i);
    }

    public void deleteItem(Item i ) { mItems.remove(i); }

    public boolean saveItems() {
        try {
            mSerializer.saveItems(mItems);
            Log.d(TAG, "items saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public Item getItem(UUID id) {
        for (Item i : mItems) {
            if (i.getId().equals(id))
                return i;
        }
        return null;
    }

}
