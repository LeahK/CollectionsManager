package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
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

public class ActivityAddCollection extends AppCompatActivity {


    //*************************
    // Variables
    //*************************
    private static final String TAG = "AddCollection";

    private ImageView mPhotoView;
    private String mCurrentPhotoPath;

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
        mPhotoView = (ImageView) findViewById(R.id.collectionThumbnail);

        // create the camera button - used to switch to camera service
        ImageButton useCamera = (ImageButton) findViewById(R.id.takePictureButton);

        // create the save button
        Button saveCollectionButton = (Button) findViewById(R.id.saveCollectionButton);

        // create the cancel button
        Button cancelCollectionButton = (Button) findViewById(R.id.cancelCollectionButton);


        //*************************
        // LISTENERS
        //*************************

        saveCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the collectionName
                String collectionNameText = collectionName.getText().toString();

                if (TextUtils.isEmpty(collectionNameText)){
                    collectionName.setError("Please enter a name for your collection.");
                }
                if (mCurrentPhotoPath == null || mCurrentPhotoPath.equals("")){
                    Toast.makeText(getApplicationContext(), "Take a picture for your collection by clicking the camera button.", Toast.LENGTH_LONG).show();
                }
                else{
                    Collection createdCollection = new Collection(collectionNameText.toString());
                    createdCollection.setPhotoPath(mCurrentPhotoPath);

                    // then save the collection

                    createdCollection.saveCollection();

                    // redirect to main page
                    Intent mainCollectionsPage = new Intent(ActivityAddCollection.this, ActivityCollections.class);
                    startActivity(mainCollectionsPage);
                    ActivityAddCollection.this.finish();
                }
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
                capturePhoto();
            }
        });

        // the image taken in the camera view will need to be saved, and then appear as the
        // thumbnail of the collection
    }

    //*************************
    // METHODS
    //*************************

    static final int REQUEST_IMAGE_CAPTURE = 1;

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

    private void setPhoto(ImageView iv) {
        File photoFile = new File (mCurrentPhotoPath);
        //Uri photoUri = Uri.fromFile(photoFile);
        if (photoFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            //mPhotoView = (ImageView)v.findViewById(R.id.item_image);
            iv.setImageBitmap(bitmap);
            //c.setPhotoPath(mCurrentPhotoPath);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult()");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setPhoto(mPhotoView);
        }
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
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
}
