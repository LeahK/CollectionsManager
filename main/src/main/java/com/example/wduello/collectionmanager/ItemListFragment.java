package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kayewrobleski on 3/24/16.
 */
public class ItemListFragment extends ListFragment {

    private static final String TAG = "ItemListFragment";
    public static final String EXTRA_COLLECTION_ID = "com.example.wduello.collectionmanager.collection_id";

    private ArrayList<Item> mItems;
    private Collection mCurrentCollection;
    private UUID collectionId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // get collection id
        Intent startingIntent = getActivity().getIntent();
        Bundle extras = startingIntent.getExtras();

        if (extras != null){
            collectionId = (UUID) extras.get("collectionId");
        }


        //Get collection based on collection ID
        mCurrentCollection = Collection.findCollectionById(collectionId);

        //Get items in collection.  If null, create a new list
        mItems = mCurrentCollection.getItemsArrayList();
        if (mItems == null) {
            mItems = new ArrayList<>();
        }

        /* TEST
        Item i1 = new Item();
        Item i2 = new Item();
        mItems.add(i1);
        mItems.add(i2); */

        ItemAdapter adapter = new ItemAdapter(mItems);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, parent, false);

        FloatingActionButton createItemButton = (FloatingActionButton) v.findViewById(R.id.fab);
        createItemButton.setVisibility(View.VISIBLE);
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Start ItemActivity
                Intent i = new Intent(getActivity(), ItemActivity.class);
                startActivity(i);

            }
        });

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = ((ItemAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, item.getItemName() + " was clicked");

        //Start ItemActivity
        Intent i = new Intent(getActivity(), ItemActivity.class);
        i.putExtra(ItemFragment.EXTRA_ITEM_ID, item.getId());
        startActivity(i);
    }


    private class ItemAdapter extends ArrayAdapter<Item> {

        public ItemAdapter(ArrayList<Item> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //If convertView is null, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.custom_list_item, null);
            }

            //Configure view for this item
            Item item = getItem(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.custom_list_item_nameTextView);
            nameTextView.setText(item.getItemName());

            CheckBox advertisedCheckBox = (CheckBox) convertView.findViewById(R.id.custom_list_item_advertisedCheckBox);
            advertisedCheckBox.setChecked(item.isAdvertised());

            return convertView;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((ItemAdapter) getListAdapter()).notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "OnCreateOptionsMenu called");
        inflater.inflate(R.menu.item_list_menu, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo ) {
        getActivity().getMenuInflater().inflate(R.menu.item_list_menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo();
        int position = info.position;
        ItemAdapter adapter = (ItemAdapter)getListAdapter();
        Item localItem = adapter.getItem(position);

        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete:
                ItemList.get(getActivity()).deleteItem(localItem);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(menuItem);
    }


}
