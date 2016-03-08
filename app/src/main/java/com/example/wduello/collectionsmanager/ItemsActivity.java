package com.example.wduello.collectionsmanager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.wduello.collectionsmanager.ItemDetailActivity.EXTRA_ITEM_COLLECTION;


public class ItemsActivity extends AppCompatActivity {

    private static final String TAG = "ItemsActivity";
    private static final String FILENAME = "items.json";

    public static final int REQUEST_ITEM_DETAILS = 1;

    private ArrayList<Item> mItems;
    private CollectionsManagerJSONSerializer mSerializer;

    private ListView mItemListView;
    private Item mItem;

    private void populuateItemList() {

        mItems = new ArrayList<>();

        for (int i=0; i<3; i++) {
            mItem = new Item();
            mItem.setCollectionId(0);
            mItem.setName("Item" + i);
            mItems.add(mItem);
        }
        /*
        try {
            mItems = mSerializer.loadItems();
        } catch (Exception e) {
            mItems = new ArrayList();
            Log.e(TAG, "Error loading items: ", e);
        }
        */
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.item_list_row, mItems);
        mItemListView.setAdapter(adapter);
    }

    public boolean saveItems() {
        try {
            mSerializer.saveItems(mItems);
            Log.d(TAG, "item saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving item: ", e);
            return false;
        }
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
                //Item item = new Item();
                //UUID id = item.getId();
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                //itemDetailIntent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, id);
                startActivityForResult(itemDetailIntent, REQUEST_ITEM_DETAILS);
            }
        });

        ArrayList<Item> items = new ArrayList<>();
        Item i1 = new Item();
        i1.setName("Item 1");
        items.add(i1);

        mItemListView = (ListView) findViewById(R.id.items_list);
        populuateItemList();
        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = mItemListView.getItemAtPosition(position);
                Item item = (Item) o;
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                itemDetailIntent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, item.getId().toString());
                startActivityForResult(itemDetailIntent, REQUEST_ITEM_DETAILS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ITEM_DETAILS && resultCode == RESULT_OK) {
            if (mItems.size() == 0) {
                mItem = new Item();
                mItems.add(mItem);
            } else {
                //Update item in array list
                for (Item item : mItems) {
                    if (item.getId() == mItem.getId()) {
                        mItem = item;
                    }
                }
            }
            mItem.setName(data.getStringExtra(ItemDetailActivity.EXTRA_ITEM_NAME));
            //mItem.setCollectionId(data.getIntExtra(EXTRA_ITEM_COLLECTION));
            Bitmap b = (Bitmap) data.getParcelableExtra(ItemDetailActivity.EXTRA_ITEM_PHOTO);
            Photo p = new Photo(b);
            mItem.setPhoto(p);
            ArrayList<Attribute> attributes = (ArrayList<Attribute>)data.getSerializableExtra(ItemDetailActivity.EXTRA_ITEM_ATTRIBUTES);
            mItem.setAttributes(attributes);
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
