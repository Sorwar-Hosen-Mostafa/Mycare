package com.bitm.mycare.customAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.widget.ImageView;

/**
 * Created by Jibunnisa on 4/10/2017.
 */

public class Imageprocess {
    public static void setPic(String mCurrentPhotoPath, ImageView mimageView) {
        // Get the dimensions of the View
        int targetW = mimageView.getWidth();
        int targetH = mimageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        //   int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        //  bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap lowbit = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
        mimageView.setImageBitmap(lowbit);
    }}
