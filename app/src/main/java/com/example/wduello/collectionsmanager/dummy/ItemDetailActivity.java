package com.example.wduello.collectionsmanager.dummy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.net.Uri;

import com.example.wduello.collectionsmanager.Attribute;
import com.example.wduello.collectionsmanager.Item;
import com.example.wduello.collectionsmanager.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemsActivity}.
 */


public class ItemDetailActivity extends AppCompatActivity {

    //Static values
    private static final String TAG = "ItemDetail";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_ATTRIBUTES = "attributes";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static String EXTRA_ITEM_ID = "com.example.wduello.collectionsmanager.ITEM_ID";
    public static String EXTRA_ITEM_COLLECTION = "com.example.wduello.collectionsmanager.ITEM_COLLECTION";
    public static String EXTRA_ITEM_NAME = "com.example.wduello.collectionsmanager.ITEM_NAME";
    public static String EXTRA_ITEM_PHOTO = "com.example.wduello.collectionsmanager.ITEM_PHOTO";
    public static String EXTRA_ITEM_ATTRIBUTES = "com.example.wduello.collectionsmanager.ITEM_ATTRIBUTES";


    //Objects for saving
    private Item mItem;
    private String mCurrentPhotoPath;

    //Widgets
    private EditText mItemName;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private LinearLayout mItemAttributesView;
    private Button mSaveButton;
    private Button mCancelButton;
    private ImageButton mCameraButton;

    //Hard coded attributes, will be deleted eventually
    private ArrayList<Attribute> mAttributes;
    private Attribute mAttribute1;
    private Attribute mAttribute2;
    private Attribute mAttribute3;

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
        EditText attributeValue = new EditText(this);
        attributeValue.setLayoutParams(textViewParams);
        attributeValue.setText(value);
        attributeValue.setPadding(0, 10, 100, 10);

        //Add new attribute to parent
        parent.addView(newAttribute);
        //Add name and value to attribute
        newAttribute.addView(attributeName);
        newAttribute.addView(attributeValue);
    }

    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        //Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void capturePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error creating image file,", ex);
            }
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void setPhoto() {
        //Get dimensions of View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        //Get dimensions of bitmap
    }

    public void setAttributes(LinearLayout itemAttributesList) {
        int childCount = itemAttributesList.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout itemAttribute =  (LinearLayout)itemAttributesList.getChildAt(i);
            TextView attributeName = (TextView)itemAttribute.getChildAt(0);
            EditText attributeValue = (EditText)itemAttribute.getChildAt(1);
            mAttributes.get(i).setName(attributeName.getText().toString());
            mAttributes.get(i).setValue(attributeValue.getText().toString());
        }
    }

    public void createItem() {
        mItem = new Item();
        mItem.setName(mItemName.getText().toString());
        Photo photo = new Photo(mCurrentPhotoPath);
        //mItem.setPhoto(photo);
        mItem.setCollectionId(0);
        LinearLayout itemAttributesLayout = (LinearLayout)findViewById(R.id.item_attributes);
        setAttributes(itemAttributesLayout);
        mItem.setAttributes(mAttributes);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate() called");

        //Initialize item name
        mItemName = (EditText)findViewById(R.id.item_name);

        if (savedInstanceState != null) {
            mItemName.setText(savedInstanceState.getString(KEY_NAME));
            //mBitmap = savedInstanceState.getParcelable(KEY_PHOTO);
//            mImageView.setImageBitmap(mBitmap);
            ArrayList<String> attributeValues = savedInstanceState.getStringArrayList(KEY_ATTRIBUTES);
            for (int i = 0; i < attributeValues.size(); i ++) {
                Attribute attribute = mAttributes.get(i);
                String value = attributeValues.get(i);
                attribute.setValue(value);
            }
        }

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

        mCameraButton = (ImageButton) findViewById(R.id.camera_button);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                capturePhoto();
            }
        });

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                createItem();
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString(EXTRA_ITEM_ID, mItem.getId().toString());
                b.putString(EXTRA_ITEM_NAME, mItem.getName());
                b.putInt(EXTRA_ITEM_COLLECTION, 0);
                b.putString(EXTRA_ITEM_PHOTO, mCurrentPhotoPath);
                b.putSerializable(EXTRA_ITEM_ATTRIBUTES, mItem.getAttributes());
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState() called");
        savedInstanceState.putString(KEY_NAME, mItemName.getText().toString());
        //savedInstanceState.putParcelable(KEY_PHOTO, mBitmap);
        ArrayList<String> attributeValues = new ArrayList<>();
        for (Attribute attribute: mAttributes) {
            String value = attribute.getValue();
            attributeValues.add(value);
        }
        savedInstanceState.putStringArrayList(KEY_ATTRIBUTES, attributeValues);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File photoFile = new File (mCurrentPhotoPath);
            Uri photoUri = Uri.fromFile(photoFile);
            if (photoFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                mImageView = (ImageView)findViewById(R.id.item_image);
                mImageView.setImageBitmap(bitmap);
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
