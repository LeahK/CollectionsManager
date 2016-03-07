package com.example.wduello.collectionsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.content.ComponentName;

import java.util.ArrayList;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemsActivity}.
 */


public class ItemDetailActivity extends AppCompatActivity {

    //Static values
    private static final String TAG = "ItemDetail";
    private static final String FILENAME = "items.json";

    //Objects for saving
    private Item mItem;
    private CollectionsManagerJSONSerializer mSerializer;

    //Widgets
    private TextView mItemName;
    private LinearLayout mItemAttributesView;
    private Button mSaveButton;
    private Button mCancelButton;
    private Button mCameraButton;
    private boolean mEditMode;

    //Hard coded attributes, will be deleted eventually
    private ArrayList<Attribute> mAttributes;
    private Attribute mAttribute1;
    private Attribute mAttribute2;
    private Attribute mAttribute3;

    private static int TAKE_PHOTO = 1;

    //This is for testing, will eventually be deleted
    private void generateAttributes() {
        mAttribute1 = new Attribute("Date", "Date purchased", "");
        mAttribute2 = new Attribute("Color", "Color", "");
        mAttribute3 = new Attribute("Price", "Purchase price", "");
        mAttributes = new ArrayList<>();
        mAttributes.add(mAttribute1);
        mAttributes.add(mAttribute2);
        mAttributes.add(mAttribute3);

    }

    private void generateAttributeTextView(LinearLayout parent, int id, String name, String value) {
        //Create linear layout for attribute
        LinearLayout newAttribute = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        newAttribute.setLayoutParams(layoutParams);
        newAttribute.setId(id);
        newAttribute.setOrientation(LinearLayout.HORIZONTAL);
        newAttribute.setGravity(Gravity.LEFT);

        //Create text view for attribute name
        TextView attributeName = new TextView(this);
        attributeName.setLayoutParams(textViewParams);
        attributeName.setText(name + ":");
        attributeName.setPadding(0, 10, 100, 10);

        //Create text view for attribute value
        TextView attributeValue = new TextView(this);
        attributeValue.setLayoutParams(textViewParams);
        attributeValue.setText(value);
        attributeValue.setPadding(0, 10, 100, 10);

        //Add new attribute to parent
        parent.addView(newAttribute);
        //Add name and value to attribute
        newAttribute.addView(attributeName);
        newAttribute.addView(attributeValue);
    }

    public void capturePhoto() {
        Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, TAKE_PHOTO);
        }
    }

    public boolean saveItem() {
        try {
            mSerializer.saveItem(mItem);
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
        setContentView(R.layout.item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate() called");




        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); */

        generateAttributes();
        Item item = new Item();
        item.setName("Item 1");
        item.setCollectionId(0);
        item.setAttributes(mAttributes);
        mItemAttributesView = (LinearLayout)findViewById(R.id.item_attributes);
        int i;
        mAttribute1.setValue("1/1/2016");
        mAttribute2.setValue("Red");
        mAttribute3.setValue("$10");
        for (i=0; i<mAttributes.size(); i++) {
            Attribute attribute = mAttributes.get(i);
            generateAttributeTextView(mItemAttributesView, i, attribute.getName(), attribute.getValue());
        }

        mCameraButton = (Button) findViewById(R.id.camera_button);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                capturePhoto();
            }
        });

        mSerializer = new CollectionsManagerJSONSerializer(ItemDetailActivity.this, FILENAME);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //Save Items
            }
        });


    }

        // Show the Up button in the action bar.
    /*
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } */

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                //Save photo
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        //Save item
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        //Load item
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
