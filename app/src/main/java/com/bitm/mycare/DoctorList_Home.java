package com.bitm.mycare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitm.mycare.customAdapter.DoctorAdapter;
import com.bitm.mycare.dao.DoctorDAO;
import com.bitm.mycare.model.Doctor;

import java.util.ArrayList;

public class DoctorList_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    ListView listView;
    private ArrayList<Doctor> doctors;
    private Doctor doctor;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private DoctorDAO doctorDAO;
    private DoctorAdapter doctorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list__home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.mydoctorslistview);


         doctors =new ArrayList<>();

         doctorDAO=new DoctorDAO(this);

         doctors = doctorDAO.getAllDoctor();

         doctorAdapter=new DoctorAdapter(this,doctors);

          listView.setAdapter(doctorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int doctorId = doctors.get(position).getId();
                String doctorName = doctors.get(position).getName();
                String doctorSpeciality = doctors.get(position).getSpeciality();
                String doctorAppointDate = doctors.get(position).getAppointmentDate();
                String doctorPhone= doctors.get(position).getPhone();
                String doctorEmail= doctors.get(position).getEmail();

                startActivity(new Intent(DoctorList_Home.this,DoctorDetails.class)
                        .putExtra("doctorId",doctorId)
                        .putExtra("doctorName",doctorName)
                        .putExtra("speciality",doctorSpeciality)
                        .putExtra("appointDate",doctorAppointDate)
                        .putExtra("doctorPhone",doctorPhone)
                        .putExtra("doctorEmail",doctorEmail)
                );
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_main);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DoctorList_Home.this,AddDoctor.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.doctor_list:

                break;
            case R.id.prescription_list:
                startActivity(new Intent(DoctorList_Home.this,PrescriptionList.class));
                break;
            case R.id.settings:

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doctor_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        logout();

    }
    private void logout(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("mypreference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in",false);
        editor.commit();
        startActivity(new Intent(DoctorList_Home.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
}
