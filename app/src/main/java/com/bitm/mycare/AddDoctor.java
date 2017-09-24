package com.bitm.mycare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bitm.mycare.dao.DoctorDAO;
import com.bitm.mycare.model.Doctor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDoctor extends AppCompatActivity {

    Calendar calendar;
    int year,month,day;
    SimpleDateFormat sdf;
    private int doctorId;
    private String name, speciality, appointDate, phoneNo, email;
    Button appointBtn;

    Toolbar toolbar;
    EditText nameET,specialityET,appointET,phoneET,emailET;
    Button saveBtn;
    Doctor doctor;
    DoctorDAO doctorDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        nameET = (EditText) findViewById(R.id.name_et);
        specialityET = (EditText) findViewById(R.id.speciality_et);

        appointBtn = (Button) findViewById(R.id.select_date_btn);
        appointBtn.setText(currentDate());

        phoneET = (EditText) findViewById(R.id.phone_et);
        emailET = (EditText) findViewById(R.id.email_et);
        saveBtn = (Button) findViewById(R.id.save_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD DOCTOR");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean nameok=false,spok=false,phoneok=false,emailok=false;
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
                if(phoneET.getText().toString().equals("")){
                    phoneET.setError("Enter doctor name");
                }
                else {
                    phoneok=true;
                }
                if(emailET.getText().toString().equals("")){
                    emailET.setError("Enter doctor name");
                }
                else {
                    emailok=true;
                }
                if(nameok&&spok&&phoneok&&emailok){
                    doctor = new Doctor();
                    doctor.setName(nameET.getText().toString());
                    doctor.setSpeciality(specialityET.getText().toString());
                    doctor.setAppointmentDate(appointBtn.getText().toString());
                    doctor.setPhone(phoneET.getText().toString());
                    doctor.setEmail(emailET.getText().toString());
                    doctorDAO = new DoctorDAO(AddDoctor.this);
                    if(doctorId > 0){
                        doctor.setId(doctorId);
                        if(doctorDAO.updateDoctor(doctor,doctorId)){
                            startActivity(new Intent(AddDoctor.this,DoctorList_Home.class));
                        }
                    }
                    else {
                        if (doctorDAO.insert(doctor) > 0) {
                            startActivity(new Intent(AddDoctor.this, DoctorList_Home.class));
                        }
                    }
                }



            }
        });

        doctorId = getIntent().getIntExtra("doctorId", 0);
        name = getIntent().getStringExtra("doctorName");
        speciality = getIntent().getStringExtra("speciality");
        appointDate = getIntent().getStringExtra("appointDate");
        phoneNo = getIntent().getStringExtra("doctorPhone");
        email = getIntent().getStringExtra("doctorEmail");
        if(doctorId > 0){
            nameET.setText(name);
            specialityET.setText(speciality);
            appointET.setText(appointDate);
            phoneET.setText(phoneNo);
            emailET.setText(email);
            saveBtn.setText("Update");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddDoctor.this,DoctorList_Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void selectdate(View view) {
        calendar = Calendar.getInstance(Locale.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(AddDoctor.this,dateSet,year,month,day);
        dateDialog.show();
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
}
