package com.bitm.mycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    boolean logged_in=false;
    Toolbar toolbar;
    EditText userNameEt, userPasswordEt;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        sharedPreferences = getSharedPreferences("mypreference", MODE_PRIVATE);

        logged_in = sharedPreferences.getBoolean("logged_in", false);

        if(logged_in==true){
            startActivity(new Intent(MainActivity.this,DoctorList_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        userNameEt = (EditText) findViewById(R.id.login_user_name_et);
        userPasswordEt = (EditText) findViewById(R.id.login_user_password_et);

    }

    public void signup(View view) {

        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }

    public void signin(View view) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("logged_in",true);
            editor.commit();
            String name = sharedPreferences.getString("user_name", null);
            String email = sharedPreferences.getString("user_email", null);
            String password = sharedPreferences.getString("user_password", null);
            boolean data = false;

            if (name == null && email == null && password == null) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("MESSAGE");
                builder.setMessage("You did not signd up yet. please sign up first");
                builder.setPositiveButton("SIGN UP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            } else {
                data = true;
            }

            if (data) {
                boolean usernameok = false, passwordok = false;
                if (userNameEt.getText().toString().equals("")) {
                    userNameEt.setError("Enter username");
                } else {
                    usernameok = true;
                }
                if (userPasswordEt.getText().toString().equals("")) {
                    userPasswordEt.setError("Enter password");

                } else {
                    passwordok = true;
                }
                if (usernameok && passwordok) {

                    if (name.equals(userNameEt.getText().toString().trim()) && password.equals(userPasswordEt.getText().toString().trim())) {

                        Toast.makeText(this, "login Successfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, DoctorList_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                    }


                }


            }



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
