package com.example.wduello.collectionsmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by kayewrobleski on 3/7/16.
 */
public class Photo {

    private static final String JSON_BITMAP = "bitmap";

    private Bitmap mBitmap;

    public Photo(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Photo(JSONObject json) throws JSONException {
        String jsonString = json.toString();
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        mBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public JSONObject toJSON()  throws JSONException {
        JSONObject json = new JSONObject();
        Bitmap bitmap = mBitmap;
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        json.put(JSON_BITMAP, encodedImage);
        return json;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}
