package com.example.wduello.collectionmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/24/16.
 */

public class ItemListActivity extends SingleFragmentActivity {

    private static final String TAG = "ItemListActivity";

    @Override
    protected Fragment createFragment() {
        return new ItemListFragment();
    }

    @Override
    public void onBackPressed() {

        // if back button was pressed restart the collections activity
        Intent i = new Intent(this, ActivityCollections.class);
        startActivity(i);
    }



}
