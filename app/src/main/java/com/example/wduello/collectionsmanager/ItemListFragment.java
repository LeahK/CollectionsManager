package com.example.wduello.collectionsmanager;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kayewrobleski on 3/24/16.
 */
public class ItemListFragment extends ListFragment {

    private static final String TAG = "ItemListFragment";

    private ArrayList<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.items_title);
        mItems = ItemList.get(getActivity()).getItems();

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
                //Create new item
                Item item = new Item();
                mItems.add(item);

                //Start ItemActivity
                Intent i = new Intent(getActivity(), ItemActivity.class);
                i.putExtra(ItemFragment.EXTRA_ITEM_ID, item.getId());
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = ((ItemAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, item.getName() + " was clicked");

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
        public View getView(int position, View convertView, ViewGroup parent) {
            //If convertView is null, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.custom_list_item, null);
            }

            //Configure view for this item
            Item item = getItem(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.custom_list_item_nameTextView);
            nameTextView.setText(item.getName());

            CheckBox advertisedCheckBox = (CheckBox)convertView.findViewById(R.id.custom_list_item_advertisedCheckBox);
            advertisedCheckBox.setChecked(item.isAdvertised());

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ItemAdapter) getListAdapter()).notifyDataSetChanged();
    }


}
