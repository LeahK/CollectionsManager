package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
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

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class ActivityCollections extends AppCompatActivity {

    //*************************
    // VARIABLES
    //*************************
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

    public void setThumbnail(ImageView iv, Collection c) {
        String photoPath = c.getPhotoPath();
        if (photoPath != null) {
            File photoFile = new File(c.getPhotoPath());
            if (photoFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false);
                iv.setImageBitmap(bitmap);
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(ActivityCollections.this.getResources(), R.drawable.ic_photo_24dp);
                bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false);
                iv.setImageBitmap(bitmap);
            }
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(ActivityCollections.this.getResources(), R.drawable.ic_photo_24dp);
            bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false);
            iv.setImageBitmap(bitmap);
        }
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
            setThumbnail(imageView, collection);
            //imageView.setImageResource(R.drawable.ic_photo_24dp);

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
        this.finish();
    }
}
