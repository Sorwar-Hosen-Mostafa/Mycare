package com.bitm.mycare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bitm.mycare.customAdapter.Imageprocess;

import uk.co.senab.photoview.PhotoViewAttacher;


public class FullScreenPrescriptionImage extends AppCompatActivity {

    ImageView imageView;
    PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_prescription_image);

        imageView = (ImageView) findViewById(R.id.imageView);



         String Image_url = getIntent().getStringExtra("image_url");

        if(Image_url!=null){
            Imageprocess.setPic(Image_url,imageView);
        }


        photoViewAttacher = new PhotoViewAttacher(imageView);



    }
}
