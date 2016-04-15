package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class ActivityCollections extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //*************************
    // VARIABLES
    //*************************

    // this ArrayList will store the IDs for the image thumbnails
    // these thumbnails will populate the gridView on the collections page
    private ArrayList<Integer> mCollectionsThumbIds = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addItem = (FloatingActionButton) findViewById(R.id.buttonAddCollection);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this button will redirect to the "add a new collection" page
                Intent intent = new Intent(ActivityCollections.this, ActivityAddCollection.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //*************************
        // Populate the thumbnail list
        //*************************

        // @TODO -- eventually this will be replaced with calls to external server

        populateThumbnails(R.drawable.ic_photo_24dp);
        populateThumbnails(R.drawable.ic_photo_24dp);
        populateThumbnails(R.drawable.ic_photo_24dp);
        populateThumbnails(R.drawable.ic_photo_24dp);
        populateThumbnails(R.drawable.ic_photo_24dp);
        populateThumbnails(R.drawable.ic_photo_24dp);

        //*************************
        // Collections/Items GRIDVIEW
        //*************************
        GridView gridView = (GridView) findViewById(R.id.gridView);
        ArrayList<Collection> collections = ActivityLogin.mCurrentUser.getCollectionsArrayList();

            final GridViewAdapter gridViewAdapter = new GridViewAdapter(this, collections);
        gridView.setAdapter(gridViewAdapter);
        gridView.setNumColumns(3);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // redirect to ItemListActivity in app folder
                Collection collection = gridViewAdapter.getItem(position);
                Intent itemListIntent = new Intent(ActivityCollections.this, ItemListActivity.class);
                itemListIntent.putExtra("collectionId", collection.getCollectionId());
                startActivity(itemListIntent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // remove item
                gridViewAdapter.removeItem(position);

                gridViewAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //*************************
    // METHODS
    //*************************

    // populate the ArrayList of thumbnails
    // @TODO --> this may have a parameter with a collection object or an item object?
    public void populateThumbnails(Integer imageResourceId){

        // add a new thumbnail to the list
        mCollectionsThumbIds.add(imageResourceId);
    }

    public class GridViewAdapter extends BaseAdapter{


        private Context mContext;
        private ArrayList<Collection> mCollections;
        private LayoutInflater mLayoutInflater = null;

        public GridViewAdapter(Activity context, ArrayList<Collection> collections) {
            mContext = context;
            mCollections = collections;
            mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // how many views are we populating?
        @Override
        public int getCount() {
            return mCollections.size();
        }

        @Override
        public Collection getItem(int position){
            return mCollections.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // this will be used to remove a collection from the gridView and from storage
        public void removeItem(int position){
            mCollectionsThumbIds.remove(position);

            Collection collectionToRemove = mCollections.get(position);
            mCollections.remove(position);
            ActivityLogin.mCurrentUser.removeCollection(collectionToRemove.getCollectionName());
            Toast.makeText(ActivityCollections.this, "Collection " + collectionToRemove.getCollectionName() +
                    " deleted", Toast.LENGTH_SHORT).show();
            ActivityLogin.mCurrentUser.saveUser();

            notifyDataSetChanged();
        }

        // note: before this view can display anything, the list containing the thumbnail images
        // MUST be populated. Otherwise, you'll get a blank view.
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.imagebutton_collection_or_item, parent, false);
            }

            Collection collection = getItem(position);

            // create an image button
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView_thumbnail);
            imageView.setImageResource(R.drawable.ic_photo_24dp);

            // associate the position with the imageButton
            imageView.setTag(Integer.valueOf(position));

            // create a textView
            TextView collectionName = (TextView) convertView.findViewById(R.id.collection_or_item_name);
            collectionName.setText(collection.getCollectionName());

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_collections_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_collections) {
            // @TODO --> placeholder for new intent ... redirect to myCollections page
            Toast.makeText(ActivityCollections.this, "MY COLLECTIONS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_advertisements) {
            // @TODO --> placeholder for new intent ... redirect to myAdvertisements page
            Intent mainAdvertisementsPage = new Intent(this, ActivityAdvertisements.class);

            Toast.makeText(ActivityCollections.this, "MY ADVERTISEMENTS!", Toast.LENGTH_SHORT).show();
            startActivity(mainAdvertisementsPage);
        } else if (id == R.id.nav_soldItems) {
// @TODO --> placeholder for new intent ... redirect to mySoldItems page
            Toast.makeText(ActivityCollections.this, "MY SOLD ITEMS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_search) {
// @TODO --> placeholder for new intent ... redirect to search page
            Toast.makeText(ActivityCollections.this, "SEARCH!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
