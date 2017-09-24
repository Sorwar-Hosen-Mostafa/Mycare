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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bitm.mycare.customAdapter.PrescriptionAdapter;
import com.bitm.mycare.dao.DoctorDAO;
import com.bitm.mycare.dao.PrescriptionDAO;
import com.bitm.mycare.model.Prescription;

import java.util.ArrayList;

public class DoctorDetails extends AppCompatActivity {

    TextView doctorNameTV, specialityTV, appointDateTV, phoneTV, emailTV;
    Button addPrescripButton;

    ArrayList<Prescription> prescriptions ;
    PrescriptionDAO prescriptionDAO;
    ListView listView;
    PrescriptionAdapter prescriptionAdapter;
    Toolbar toolbar;
    private int doctorId;
    private String name, speciality, appointDate, phoneNo, email;
    DoctorDAO doctorDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        doctorDao = new DoctorDAO(this);
        doctorId = getIntent().getIntExtra("doctorId", 0);
        name = getIntent().getStringExtra("doctorName");
        speciality = getIntent().getStringExtra("speciality");
        appointDate = getIntent().getStringExtra("appointDate");
        phoneNo = getIntent().getStringExtra("doctorPhone");
        email = getIntent().getStringExtra("doctorEmail");

        listView= (ListView)findViewById(R.id.pres_list);

        prescriptions = new ArrayList<>();
        prescriptionDAO = new PrescriptionDAO(this);
        prescriptions = prescriptionDAO.getselecteddoctorsprescription(doctorId);
        prescriptionAdapter = new PrescriptionAdapter(this,prescriptions);
        listView.setAdapter(prescriptionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int prescriptionid = prescriptions.get(position).getId();
                String image_url = prescriptions.get(position).getImageUrl();
                String prescriptionDate = prescriptions.get(position).getPrescriptionDate();
                String doctorname = prescriptions.get(position).getDoctor().getName();
                String prescritionDescription = prescriptions.get(position).getPrescriptionDescription();

                startActivity(new Intent(DoctorDetails.this,PrescriptionDetails.class)
                        .putExtra("pres_id",prescriptionid)
                        .putExtra("doctorName",doctorname)
                        .putExtra("date",prescriptionDate)
                        .putExtra("image_url",image_url)
                        .putExtra("prescription_desc",prescritionDescription)
                );
            }
        });


        toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DOCTOR");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        doctorNameTV = (TextView) findViewById(R.id.doctor_name_tv);
        specialityTV = (TextView) findViewById(R.id.doctor_speciality_tv);
        appointDateTV = (TextView) findViewById(R.id.doctor_appointment_tv);
        phoneTV = (TextView) findViewById(R.id.doctor_phone_tv);
        emailTV = (TextView) findViewById(R.id.doctor_email_tv);
        addPrescripButton = (Button) findViewById(R.id.prescription_btn);
        addPrescripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetails.this,AddPrescription.class)
                        .putExtra("doctorId",doctorId)
                        .putExtra("doctorName",name)
                        .putExtra("speciality",speciality)
                        .putExtra("appointDate",appointDate)
                        .putExtra("doctorPhone",phoneNo)
                        .putExtra("doctorEmail",email)
                );
            }
        });
        doctorNameTV.setText(name);
        specialityTV.setText(speciality);
        appointDateTV.setText(appointDate);
        phoneTV.setText(phoneNo);
        emailTV.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doctor_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.doctor_update_menu:
                updateDoctor();
                return true;
            case R.id.doctor_delete_menu:
                deleteDoctor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDoctor() {
        startActivity(new Intent(DoctorDetails.this,AddDoctor.class)
                .putExtra("doctorId",doctorId)
                .putExtra("doctorName",name)
                .putExtra("speciality",speciality)
                .putExtra("appointDate",appointDate)
                .putExtra("doctorPhone",phoneNo)
                .putExtra("doctorEmail",email)
        );
    }
    private void deleteDoctor() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Doctor???");
        alert.setMessage("Are you sure to delte the doctor? ");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(doctorDao.deleteDoctor(doctorId)){
                    startActivity(new Intent(DoctorDetails.this,DoctorList_Home.class));
                }
            }
        });
        alert.setNegativeButton("Cancel",null);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DoctorDetails.this,DoctorList_Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
