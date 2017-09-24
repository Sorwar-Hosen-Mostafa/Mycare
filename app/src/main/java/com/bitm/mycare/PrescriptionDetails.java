package com.bitm.mycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bitm.mycare.customAdapter.Imageprocess;
import com.bitm.mycare.dao.PrescriptionDAO;

public class PrescriptionDetails extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageView;
    int id;
    TextView doctorname,date,description;
    PrescriptionDAO prescriptionDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PRESCRIPTION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        imageView = (ImageView) findViewById(R.id.prescription_image);
        doctorname = (TextView) findViewById(R.id.doctor_name_tv);
        date = (TextView) findViewById(R.id.date_tv);
        description = (TextView) findViewById(R.id.description_tv);

        final String  image_url = getIntent().getStringExtra("image_url");
        String  doctor_name = getIntent().getStringExtra("doctorName");
        String  date_time = getIntent().getStringExtra("date");
        String  presctiption_description = getIntent().getStringExtra("prescription_desc");
        id = getIntent().getIntExtra("pres_id",0);


        if(image_url!=null){
            Imageprocess.setPic(image_url,imageView);
        }
        doctorname.setText(doctor_name);
        date.setText(date_time);
        description.setText(presctiption_description);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PrescriptionDetails.this,FullScreenPrescriptionImage.class).putExtra("image_url",image_url));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case  R.id.doctor_update_menu:

                return true;
            case R.id.doctor_delete_menu:

                deletprescription();
                return true;

        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doctor_detail_menu, menu);
        return true;
    }
    private void deletprescription() {
        prescriptionDAO = new PrescriptionDAO(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Prescription???");
        alert.setMessage("Are you sure to delte this prescription? ");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(prescriptionDAO.deleteprescription(id)){
                    startActivity(new Intent(PrescriptionDetails.this,PrescriptionList.class));
                }
            }
        });
        alert.setNegativeButton("Cancel",null);
        alert.show();
    }
}
