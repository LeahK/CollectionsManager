package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddCollection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_collection);

        //*************************
        // TOOLBAR
        //*************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // create the collection name textView
        TextView collectionName = (TextView) findViewById(R.id.collectionName);

        // create the collection thumbnail image
        ImageView collectionThumbnail = (ImageView) findViewById(R.id.collectionThumbnail);

        // create the camera button - used to switch to camera service
        ImageButton useCamera = (ImageButton) findViewById(R.id.takePictureButton);

        //*************************
        // BEGIN CAMERA SERVICE
        //*************************

        // the on-click listener for this button will transition to the camera service
        //useCamera.setOnClickListener(...);

        // the image taken in the camera view will need to be saved, and then appear as the
        // thumbnail of the collection

        //*************************
        // FIELD LIST VIEW
        //*************************



        //*************************
        // SIDEBAR
        //*************************

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //*************************
    // METHODS
    //*************************

    //*************************
    // FIELD LIST VIEW
    //*************************

    // the FieldSpinnerActivity class will allow us to react based on which field TYPE the
    // user has chosen from the drop-down
    public class FieldSpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
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
        getMenuInflater().inflate(R.menu.add_collection, menu);
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
            Toast.makeText(ActivityAddCollection.this, "MY COLLECTIONS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_advertisements) {
            // @TODO --> placeholder for new intent ... redirect to myAdvertisements page
            Toast.makeText(ActivityAddCollection.this, "MY ADVERTISEMENTS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_soldItems) {
// @TODO --> placeholder for new intent ... redirect to mySoldItems page
            Toast.makeText(ActivityAddCollection.this, "MY SOLD ITEMS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_search) {
// @TODO --> placeholder for new intent ... redirect to search page
            Toast.makeText(ActivityAddCollection.this, "SEARCH!", Toast.LENGTH_SHORT).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
