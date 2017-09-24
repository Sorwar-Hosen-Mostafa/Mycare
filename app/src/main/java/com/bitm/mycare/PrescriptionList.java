package com.bitm.mycare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitm.mycare.customAdapter.PrescriptionAdapter;
import com.bitm.mycare.dao.PrescriptionDAO;
import com.bitm.mycare.model.Prescription;

import java.util.ArrayList;

public class PrescriptionList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ArrayList<Prescription> prescriptions = null;
    ListView prescriptionLV;
    PrescriptionDAO prescriptionDAO;
    PrescriptionAdapter adapter;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PRESCRIPTION LIST");



        prescriptionLV = (ListView) findViewById(R.id.prescription_lv);
        prescriptions = new ArrayList<>();
        prescriptionDAO = new PrescriptionDAO(this);
        prescriptions = prescriptionDAO.getAllPrescription();
        adapter = new PrescriptionAdapter(this,prescriptions);
        prescriptionLV.setAdapter(adapter);

        prescriptionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int prescriptionid = prescriptions.get(position).getId();
                String image_url = prescriptions.get(position).getImageUrl();
                String prescriptionDate = prescriptions.get(position).getPrescriptionDate();
                String doctorname = prescriptions.get(position).getDoctor().getName();
                String prescritionDescription = prescriptions.get(position).getPrescriptionDescription();

                startActivity(new Intent(PrescriptionList.this,PrescriptionDetails.class)
                        .putExtra("pres_id",prescriptionid)
                        .putExtra("doctorName",doctorname)
                        .putExtra("date",prescriptionDate)
                        .putExtra("image_url",image_url)
                        .putExtra("prescription_desc",prescritionDescription)
                );
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_main);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.doctor_list:
                onBackPressed();
                break;
            case R.id.prescription_list:

                break;
            case R.id.settings:

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PrescriptionList.this,DoctorList_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
