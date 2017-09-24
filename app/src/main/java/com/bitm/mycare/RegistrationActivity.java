package com.bitm.mycare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText userNameEt,userEmailEt,userPasswordET;
    private Button signUpbtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("REGISTRATION");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNameEt = (EditText)findViewById(R.id.registration_user_name_et);
        userEmailEt = (EditText)findViewById(R.id.registration_user_email_et);
        userPasswordET = (EditText)findViewById(R.id.registration_user_password_et);

        sharedPreferences = getSharedPreferences("mypreference",MODE_PRIVATE);

        editor = sharedPreferences.edit();

    }

    public void singupconfirm(View view) {



        String name = userNameEt.getText().toString().trim();
        String email = userEmailEt.getText().toString().trim();
        String password = userPasswordET.getText().toString().trim();
        boolean nameok=false,emailok=false,passwordok=false;
        if(name.equals("")){
            userNameEt.setError("Enter your name");
        }
        else {
            nameok=true;
        }
        if (email.equals("")){
            userEmailEt.setError("Enter your email");

        }
        else {
            emailok=true;
        }
        if (password.equals("")){
            userPasswordET.setError("Enter your password");

        }
        else {
            passwordok=true;
        }
        if(nameok && emailok && passwordok){
            editor.putString("user_name",name);
            editor.putString("user_email",email);
            editor.putString("user_password",password);
            editor.commit();
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegistrationActivity.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
