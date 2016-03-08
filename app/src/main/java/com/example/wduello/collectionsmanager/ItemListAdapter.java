package com.example.wduello.collectionsmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by kayewrobleski on 3/7/16.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

    public ItemListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ItemListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_row, null);
        }

        Item p = getItem(position);

        if (p != null) {
            TextView itemTextView = (TextView) v.findViewById(R.id.itemTextView);
            //ImageView itemImageView = (ImageView) v.findViewById(R.id.itemImageView);

            if (itemTextView != null) {
                itemTextView.setText(p.getName());
            }

            //Will eventually be set to get image for item
            //itemImageView.setImageResource(R.drawable.ic_menu_gallery);
            /*
            if (itemImageView != null) {
                itemImageView.setImageResource(R.drawable.ic_menu_gallery);
            } */
        }

        return v;
    }
}
