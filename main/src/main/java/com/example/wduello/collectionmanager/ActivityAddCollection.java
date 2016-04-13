package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityAddCollection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //*************************
    // Variables
    //*************************

    // this ArrayList will store the field ids
    private ArrayList<Integer> mCollectionsThumbIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_collection);

        //*************************
        // TOOLBAR
        //*************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //*************************
        // BUTTONS
        //*************************

        // create the collection name editText
        final EditText collectionName = (EditText) findViewById(R.id.collectionName);

        // create the collection thumbnail image
        ImageView collectionThumbnail = (ImageView) findViewById(R.id.collectionThumbnail);

        // create the camera button - used to switch to camera service
        ImageButton useCamera = (ImageButton) findViewById(R.id.takePictureButton);

        // create the add new field button
        Button addFieldButton = (Button) findViewById(R.id.addFieldButton);

        // @TODO -- implement this later
        addFieldButton.setVisibility(View.INVISIBLE);

        // create the save button
        Button saveCollectionButton = (Button) findViewById(R.id.saveCollectionButton);

        // create the cancel button
        Button cancelCollectionButton = (Button) findViewById(R.id.cancelCollectionButton);

        //*************************
        // ListView
        //*************************

//        ListView listView = (ListView) findViewById(R.id.listView_fields);
//        final ListViewAdapter listViewAdapter = new ListViewAdapter(this);
//        listView.setAdapter(listViewAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // redirect to itemDetail view
//                Intent itemDetailIntent = getPackageManager().getLaunchIntentForPackage("com.example.wduello.collectionsmanager");
//                startActivity(itemDetailIntent);
//            }
//        });

        //*************************
        // LISTENERS
        //*************************

        addFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // this should add a spinner
//                Spinner spinner = (Spinner) findViewById(R.id.spinner_field_type);
//
//                // Create an ArrayAdapter using the string array and a default spinner layout
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
//                        R.array.field_types, android.R.layout.simple_spinner_item);
//
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                // Apply the adapter to the spinner
//                spinner.setAdapter(adapter);
            }
        });

        saveCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the collectionName
                CharSequence collectionNameText = collectionName.getText();

                Collection createdCollection = new Collection(collectionNameText.toString());

                // then save the collection
                createdCollection.saveCollection();

                // redirect to myAdvertisements page
                Intent mainCollectionsPage = new Intent(ActivityAddCollection.this, ActivityCollections.class);
                startActivity(mainCollectionsPage);

            }
        });

        cancelCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this should just take the user back to the previous page
                finish();
            }
        });

        //*************************
        // BEGIN CAMERA SERVICE
        //*************************

        // the on-click listener for this button will transition to the camera service
        useCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when you click the camera button, it should open camera service
                dispatchTakePictureIntent();
            }
        });

        // the image taken in the camera view will need to be saved, and then appear as the
        // thumbnail of the collection

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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.collectionThumbnail);
            imageView.setImageBitmap(imageBitmap);

            // get the path

            //Create an image file name
            String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                image = File.createTempFile(imageFileName, ".jpg", storageDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Save a file: path for use with ACTION_VIEW intents
            //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
            String mCurrentPhotoPath = image.getAbsolutePath();

            // save the photo path to the collection
            // collection.setPhotoPath(mCurrentPhotoPath);
        }
    }

    //*************************
    // FIELD LIST VIEW
    //*************************

    // the list view adapter will go here

    // the FieldSpinnerActivity class will allow us to react based on which field TYPE the
    // user has chosen from the drop-down

//    public class FieldSpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
//
//        public void onItemSelected(AdapterView<?> parent, View view,
//                                   int pos, long id) {
//            // An item was selected. You can retrieve the selected item using
//            // parent.getItemAtPosition(pos)
//            if (parent.getItemAtPosition(pos) == "Price"){
//
//            }
//            if (parent.getItemAtPosition(pos) == "Date"){
//
//            }
//            if (parent.getItemAtPosition(pos) == "Text"){
//
//            }
//        }

//        public void onNothingSelected(AdapterView<?> parent) {
//            // Another interface callback
//        }
//    }

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
            // redirect to myCollections page
            Toast.makeText(ActivityAddCollection.this, "MY COLLECTIONS!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_advertisements) {
            // redirect to myAdvertisements page
            Intent mainAdvertisementsPage = new Intent(ActivityAddCollection.this, ActivityAdvertisements.class);
            startActivity(mainAdvertisementsPage);

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
