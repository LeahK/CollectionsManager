package com.example.wduello.collectionmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by kayewrobleski on 3/24/16.
 */

public class ItemListActivity extends SingleFragmentActivity {

    private static final String TAG = "ItemListActivity";

    @Override
    protected Fragment createFragment() {
        return new ItemListFragment();
    }



}
