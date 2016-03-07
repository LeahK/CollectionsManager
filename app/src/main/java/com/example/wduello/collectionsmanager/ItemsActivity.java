package com.example.wduello.collectionsmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class ItemsActivity extends AppCompatActivity {

    private ListView mItemList;

    private void populuateItemList(ArrayList<Item> items) {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_list_row, R.id.itemTextView, items);
        mItemList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                startActivity(itemDetailIntent);
            }
        });

        ArrayList<Item> items = new ArrayList<>();
        Item i1 = new Item();
        i1.setName("Item 1");
        items.add(i1);

        mItemList = (ListView) findViewById(R.id.items_list);
        populuateItemList(items);
        mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = mItemList.getItemAtPosition(position);
                Item item = (Item) o;
                Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDetailActivity.class);
                startActivity(itemDetailIntent);
            }
        });
    }

}
