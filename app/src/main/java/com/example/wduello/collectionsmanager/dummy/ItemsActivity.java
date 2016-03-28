package com.example.wduello.collectionsmanager.dummy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;

import com.example.wduello.collectionsmanager.Attribute;
import com.example.wduello.collectionsmanager.CollectionsManagerJSONSerializer;
import com.example.wduello.collectionsmanager.LocalItem;
import com.example.wduello.collectionsmanager.R;

import java.util.ArrayList;


public class ItemsActivity extends AppCompatActivity {

    private static final String TAG = "ItemsActivity";
    private static final String FILENAME = "items.json";

    public static final int REQUEST_ITEM_DETAILS = 1;

    private ArrayList<LocalItem> mLocalItems;
    private CollectionsManagerJSONSerializer mSerializer;

    private ListView mItemListView;
    private LocalItem mLocalItem;

    private void populuateItemList() {

        mLocalItems = new ArrayList<>();

        for (int i=0; i<3; i++) {
            mLocalItem = new LocalItem();
            mLocalItem.setCollectionId(0);
            mLocalItem.setName("LocalItem" + i);
            mLocalItems.add(mLocalItem);
        }
        /*
        try {
            mLocalItems = mSerializer.loadItems();
        } catch (Exception e) {
            mLocalItems = new ArrayList();
            Log.e(TAG, "Error loading items: ", e);
        }
        */
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.item_list_row, mLocalItems);
        mItemListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Serializer
        mSerializer = new CollectionsManagerJSONSerializer(ItemsActivity.this, FILENAME);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                //LocalItem item = new LocalItem();
                //UUID id = item.getId();
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                //itemDetailIntent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, id);
                startActivityForResult(itemDetailIntent, REQUEST_ITEM_DETAILS);
            }
        });

        ArrayList<LocalItem> localItems = new ArrayList<>();
        LocalItem i1 = new LocalItem();
        i1.setName("LocalItem 1");
        localItems.add(i1);

        mItemListView = (ListView) findViewById(R.id.items_list);
        populuateItemList();
        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = mItemListView.getItemAtPosition(position);
                LocalItem localItem = (LocalItem) o;
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                itemDetailIntent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, localItem.getId().toString());
                startActivityForResult(itemDetailIntent, REQUEST_ITEM_DETAILS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ITEM_DETAILS && resultCode == RESULT_OK) {
            if (mLocalItems.size() == 0) {
                mLocalItem = new LocalItem();
                mLocalItems.add(mLocalItem);
            } else {
                //Update item in array list
                for (LocalItem localItem : mLocalItems) {
                    if (localItem.getId() == mLocalItem.getId()) {
                        mLocalItem = localItem;
                    }
                }
            }
            mLocalItem.setName(data.getStringExtra(ItemDetailActivity.EXTRA_ITEM_NAME));
            //mLocalItem.setCollectionId(data.getIntExtra(EXTRA_ITEM_COLLECTION));
            //Photo photo = new Photo(data.getStringExtra(ItemDetailActivity.EXTRA_ITEM_PHOTO));
            //mLocalItem.setPhoto(photo);
            ArrayList<Attribute> attributes = (ArrayList<Attribute>)(data.getSerializableExtra(ItemDetailActivity.EXTRA_ITEM_ATTRIBUTES));
            mLocalItem.setAttributes(attributes);
            /*
            boolean saveSuccessful = saveItems();
            if (saveSuccessful) {
                Log.i(TAG, "Items saved successfully.");
            } else {
                Log.e(TAG, "Error saving items.");
            } */
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        //Save item
        /*
        boolean saveSuccessful = saveItems();
        if (saveSuccessful) {
            Log.i(TAG, "Items saved successfully.");
        } else {
            Log.e(TAG, "Error saving items.");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        populuateItemList();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
