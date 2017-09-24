package com.bitm.mycare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitm.mycare.model.Doctor;

import java.util.ArrayList;

/**
 * Created by Alamgir on 04/07/2017.
 */

public class DoctorDAO {

    public static final String DOCTOR_TABLE = "tbl_doctor";

    public static final String COLUMN_ID = "doctor_id";
    public static final String COLUMN_NAME = "doctor_name";
    public static final String COLUMN_SPECIALITY = "doctor_speciality";
    public static final String COLUMN_APPOINT_DATE = "doctor_appoint_date";
    public static final String COLUMN_PHONE = "doctor_phone";
    public static final String COLUMN_EMAIL = "doctor_email";
    public static final String CREATE_TABLE = "create table "+DOCTOR_TABLE +
            "( "+COLUMN_ID+" integer primary key, "+
            COLUMN_NAME+" text, "+
            COLUMN_SPECIALITY+" text, "+
            COLUMN_APPOINT_DATE+" text, "+
            COLUMN_PHONE+" text, "+
            COLUMN_EMAIL +" text);";


    private  CareDatabaseHelper careDatabaseHelper;
    private SQLiteDatabase db;

    public DoctorDAO(Context context) {
        careDatabaseHelper = new CareDatabaseHelper(context);
        open();
    }
    public void open() {
        db = careDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        careDatabaseHelper.close();
    }
    public long insert(Doctor doctor){

        ContentValues values = new ContentValues();
        putContentValues(values,doctor);


        long id = db.insert(DOCTOR_TABLE,COLUMN_NAME,values);
        close();
        return id;
    }

    private void putContentValues(ContentValues values, Doctor doctor) {
        values.put(COLUMN_NAME,doctor.getName());
        values.put(COLUMN_SPECIALITY,doctor.getSpeciality());
        values.put(COLUMN_APPOINT_DATE,doctor.getAppointmentDate());
        values.put(COLUMN_PHONE,doctor.getPhone());
        values.put(COLUMN_EMAIL,doctor.getEmail());
    }

    public ArrayList<Doctor> getAllDoctor(){
        ArrayList<Doctor> doctors = new ArrayList<>();
        Cursor cursor = db.query(DOCTOR_TABLE,null,null,null,null,null,null);
        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
           for (int i =0;i<cursor.getCount();i++){
               int doctorId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
               String doctorName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
               String doctorSpeciality = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALITY));
               String doctorAppoinmentDate = cursor.getString(cursor.getColumnIndex(COLUMN_APPOINT_DATE));
               String doctorPhone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
               String doctorEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
               doctors.add(new Doctor(doctorId,doctorName,doctorSpeciality,doctorAppoinmentDate,doctorPhone,doctorEmail));
cursor.moveToNext();
           }
        }
        cursor.close();
        this.close();
        return doctors;
    }
    public boolean updateDoctor(Doctor doctor,int id){
        ContentValues values = new ContentValues();
        putContentValues(values,doctor);
        this.open();
        int rowId =  db.update(DOCTOR_TABLE,values,COLUMN_ID+" = ?",new String[]{Integer.toString(id)});

        this.close();
        if(rowId > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean deleteDoctor(int id) {
        this.open();
        int deleteId = db.delete(DOCTOR_TABLE, COLUMN_ID + " = ?", new String[]{Integer.toString(id)});
        this.close();
        if (deleteId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
