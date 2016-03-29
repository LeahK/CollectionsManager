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
    private UUID mCollectionId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //getActivity().setTitle(R.string.items_title);
        Bundle args = getActivity().getIntent().getExtras();
        mCollectionId = (UUID)args.getSerializable(EXTRA_COLLECTION_ID);
        Collection collection = CollectionList.get(getActivity()).getCollection(mCollectionId);
        mItems = collection.getItemsArrayList();
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        ItemAdapter adapter = new ItemAdapter(mItems);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, parent, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new localItem
                Item item = new Item();
                //localItem.setCollectionId(mCollectionId);
                ItemList.get(getActivity()).addItem(item);

                //Start ItemActivity
                Intent i = new Intent(getActivity(), ItemActivity.class);
                i.putExtra(ItemFragment.EXTRA_ITEM_ID, item.getId());
                startActivity(i);
            }
        });

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item localItem = ((ItemAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, localItem.getItemName() + " was clicked");

        //Start ItemActivity
        Intent i = new Intent(getActivity(), ItemActivity.class);
        i.putExtra(ItemFragment.EXTRA_ITEM_ID, localItem.getId());
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
