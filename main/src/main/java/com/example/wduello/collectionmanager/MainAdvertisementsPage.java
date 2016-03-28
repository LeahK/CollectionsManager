package com.example.wduello.collectionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainAdvertisementsPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //*************************
    // VARIABLES
    //*************************

    // this ArrayList will store the IDs for the image thumbnails
    // these thumbnails will populate the gridView on the collections page
    private ArrayList<Integer> mAdvertisementsThumbIds = new ArrayList<Integer>();


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
                /*
                Snackbar.make(view, "ADD NEW ITEM", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
                Intent itemDetailIntent = getPackageManager().getLaunchIntentForPackage("com.example.wduello.collectionsmanager");
                startActivity(itemDetailIntent);

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
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(this);
        gridView.setAdapter(gridViewAdapter);
        gridView.setNumColumns(3);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // redirect to itemDetail view
                Intent itemDetailIntent = getPackageManager().getLaunchIntentForPackage("com.example.wduello.collectionsmanager");
                startActivity(itemDetailIntent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // remove the selected collection
                mAdvertisementsThumbIds.remove(position);

                // refresh the view
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
        mAdvertisementsThumbIds.add(imageResourceId);
    }

    public class GridViewAdapter extends BaseAdapter{

        private Context mContext;

        public GridViewAdapter(Context c){
            mContext = c;
        }

        // how many views are we populating?
        @Override
        public int getCount() {
            return mAdvertisementsThumbIds.size();
        }

        @Override
        public Object getItem(int arg0){
            return mAdvertisementsThumbIds.get(arg0);
        }

        @Override
        public long getItemId(int arg0){
            return arg0;
        }

        // note: before this view can display anything, the list containing the thumbnail images
        // MUST be populated. Otherwise, you'll get a blank view.
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
            {
                view = getLayoutInflater().inflate(R.layout.imagebutton_collection_or_item, parent, false);
            }

            // create an image button
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView_thumbnail);

            // set the image thumbnail to the one from mCollectionsThumbIds
            imageView.setImageResource(mAdvertisementsThumbIds.get(position));

            // associate the position with the imageButton
            imageView.setTag(Integer.valueOf(position));

            // create a textView
            TextView collection_or_item_name = (TextView) view.findViewById(R.id.collection_or_item_name);

            // set the text to the name of the collection or item
            // @TODO --> replace with actual text
            collection_or_item_name.setText("Advertisement");

            // when done setting all the text and shtuff
            // return the view
            return view;
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
            Intent mainCollectionsPage = new Intent(this, MainAdvertisementsPage.class);
            Toast.makeText(MainAdvertisementsPage.this, "MY COLLECTIONS!", Toast.LENGTH_SHORT).show();
            startActivity(mainCollectionsPage);
        } else if (id == R.id.nav_advertisements) {
            // @TODO --> placeholder for new intent ... redirect to myAdvertisements page
            // do nothing
            Toast.makeText(MainAdvertisementsPage.this, "MY ADVERTISEMENTS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_soldItems) {
// @TODO --> placeholder for new intent ... redirect to mySoldItems page
            Toast.makeText(MainAdvertisementsPage.this, "MY SOLD ITEMS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_search) {
// @TODO --> placeholder for new intent ... redirect to search page
            Toast.makeText(MainAdvertisementsPage.this, "SEARCH!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
