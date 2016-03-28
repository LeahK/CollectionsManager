package com.example.wduello.collectionmanager;

//import android.app.FragmentManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class ItemActivity extends SingleFragmentActivity implements ItemFragment.OnFragmentInteractionListener {


    private static final String TAG = "ItemActivity";

    @Override
    protected Fragment createFragment() {
                UUID itemId = (UUID) getIntent()
                        .getSerializableExtra(ItemFragment.EXTRA_ITEM_ID);
        return ItemFragment.newInstance(itemId);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "Interaction with ItemFragment");
    }

}
