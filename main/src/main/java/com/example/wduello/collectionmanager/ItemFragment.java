package com.example.wduello.collectionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ItemFragment extends Fragment {

    private static final String TAG = "ItemFragment";
    public static final String EXTRA_ITEM_ID = "com.example.wduello.collectionsmanager.item_id";
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    //Private members
    private Item mItem;
    private EditText mNameField;
    private CheckBox mAdvertisedCheckBox;
    private ImageView mPhotoView;
    private String mCurrentPhotoPath;
    private FloatingActionButton mDeleteButton;
    private int mCollectionId;

    private OnFragmentInteractionListener mListener;


    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(UUID itemId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEM_ID, itemId);

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);

        return fragment;
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
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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

    public void setPhoto() {
        File photoFile = new File (mCurrentPhotoPath);
        //Uri photoUri = Uri.fromFile(photoFile);
        if (photoFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            //mPhotoView = (ImageView)v.findViewById(R.id.item_image);
            mPhotoView.setImageBitmap(bitmap);
            mItem.setPhotoPath(mCurrentPhotoPath);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID itemId = (UUID) getArguments().getSerializable(EXTRA_ITEM_ID);
        //mItem = ItemList.get(getActivity()).getItem(itemId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        mNameField = (EditText) v.findViewById(R.id.item_name);
        mNameField.setText(mItem.getItemName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mItem.setItemName(c.toString());
            }

            @Override
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                //Left blank
            }

            @Override
            public void afterTextChanged(Editable c) {
                //Left blank
            }
        });

        mAdvertisedCheckBox = (CheckBox)v.findViewById(R.id.item_advertised);
        mAdvertisedCheckBox.setChecked(mItem.isAdvertised());
        mAdvertisedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItem.setAdvertised(isChecked);
            }
        });


        mPhotoView = (ImageView) v.findViewById(R.id.item_image);
        //mCurrentPhotoPath = mLocalItem.getPhotoPath();
        if (mItem.getPhotoPath() != null) {
            mCurrentPhotoPath = mItem.getPhotoPath();
            setPhoto();
        }
        ImageButton cameraButton = (ImageButton) v.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                capturePhoto();
            }
        });

        mDeleteButton = (FloatingActionButton)v.findViewById(R.id.fab_delete_item);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ItemList.get(getActivity()).deleteItem(mItem);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult()");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setPhoto();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause() called");
        //ItemList.get(getActivity()).saveItems();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
