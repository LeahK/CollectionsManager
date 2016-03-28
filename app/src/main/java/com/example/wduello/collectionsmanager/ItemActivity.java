package com.example.wduello.collectionsmanager;

//import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.UUID;

public class ItemActivity extends SingleFragmentActivity implements ItemFragment.OnFragmentInteractionListener {


    private static final String TAG = "ItemActivity";

    @Override
    protected Fragment createFragment() {
        UUID itemId = (UUID)getIntent()
                .getSerializableExtra(ItemFragment.EXTRA_ITEM_ID);
        return ItemFragment.newInstance(itemId);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "Interaction with ItemFragment");
    }

}
