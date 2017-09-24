package com.bitm.mycare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitm.mycare.customAdapter.Imageprocess;
import com.bitm.mycare.dao.DoctorDAO;
import com.bitm.mycare.dao.PrescriptionDAO;
import com.bitm.mycare.model.Doctor;
import com.bitm.mycare.model.Prescription;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPrescription extends AppCompatActivity {

    String mCurrentPhotoPath = null;
    Toolbar toolbar;
    Calendar calendar;
    Bitmap imageBitmap;
    Doctor doctor;
    DoctorDAO doctorDAO;
    ImageView imageView;
    int docid;
    Button appointBtn;
    SimpleDateFormat sdf;
    int year,month,day;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText nameET,specialityET,descriptionET;
    private static final int CONTENT_REQUEST=1337;
    private int doctorId;
    private String name, speciality, appointDate, phoneNo, email;
    Prescription objPrescription;
    PrescriptionDAO prescriptionDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        prescriptionDAO = new PrescriptionDAO(this);
        nameET = (EditText) findViewById(R.id.doctor_name_et);
        specialityET = (EditText) findViewById(R.id.doctor_speciality_et);
        descriptionET =(EditText)findViewById(R.id.prescription_description_et);



        appointBtn = (Button) findViewById(R.id.select_date_btn);

        appointBtn.setText(currentDate());

        appointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance(Locale.getDefault());
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(AddPrescription.this,dateSet,year,month,day);
                dateDialog.show();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD PRESCRIPTION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        doctorId =getIntent().getIntExtra("doctorId",0);
        docid = doctorId-1;
        name = getIntent().getStringExtra("doctorName");
        speciality = getIntent().getStringExtra("speciality");
        appointDate = getIntent().getStringExtra("appointDate");
        phoneNo = getIntent().getStringExtra("doctorPhone");
        email = getIntent().getStringExtra("doctorEmail");
        if(doctorId > 0){
            nameET.setText(name);
            specialityET.setText(speciality);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doctor_detail_menu, menu);
        return true;
    }

    public void savePrescription(View view) {

        boolean nameok=false,spok=false,deskok=false;
        if(nameET.getText().toString().equals("")){
            nameET.setError("Enter doctor name");
        }
        else {
            nameok=true;
        }
        if(specialityET.getText().toString().equals("")){
            specialityET.setError("Enter doctor speciality");
        }
        else {
            spok=true;
        }
        if(descriptionET.getText().toString().equals("")){
            descriptionET.setError("Enter description: For what kind sickness you met the doctor describe it.");
        }
        else {
            deskok=true;
        }

        if(deskok){
            doctor = new Doctor();
            doctorDAO = new DoctorDAO(AddPrescription.this);
            ArrayList<Doctor> doctors=doctorDAO.getAllDoctor();


            doctor.setId(docid);
            doctor.setName(doctors.get(docid).getName());
            doctor.setSpeciality(doctors.get(docid).getSpeciality());
            doctor.setAppointmentDate(appointBtn.getText().toString());
            doctor.setPhone(doctors.get(docid).getPhone());
            doctor.setEmail(doctors.get(docid).getEmail());
            doctorDAO.updateDoctor(doctor,doctorId);
            objPrescription = new Prescription();
            objPrescription.setPrescriptionDate(appointBtn.getText().toString());

            objPrescription.setDoctorId(doctorId);
            objPrescription.setImageUrl(mCurrentPhotoPath);
            objPrescription.setPrescriptionDescription(descriptionET.getText().toString());
            if(prescriptionDAO.insert(objPrescription)){
                startActivity(new Intent(AddPrescription.this,PrescriptionList.class));
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void takeimagefromcamera(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            imageView = (ImageView)findViewById(R.id.add_prescription_image_thumbnail);
            Imageprocess.setPic(mCurrentPhotoPath,imageView);
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void selectdate(View view) {

    }

    private String currentDate() {
        calendar = Calendar.getInstance(Locale.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year,month,day);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString= sdf.format(calendar.getTime());
        return dateString;
    }
    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            calendar.set(year,month,dayOfMonth);
            String newDate = sdf.format(calendar.getTime());
            appointBtn.setText(newDate);
        }
    };
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddPrescription.this,DoctorDetails.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
