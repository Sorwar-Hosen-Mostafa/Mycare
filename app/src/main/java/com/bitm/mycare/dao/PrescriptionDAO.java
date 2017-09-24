package com.bitm.mycare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitm.mycare.model.Doctor;
import com.bitm.mycare.model.Prescription;


import java.util.ArrayList;

/**
 * Created by Alamgir on 04/08/2017.
 */

public class PrescriptionDAO {
    public static final String TABLE = "tbl_prescription";
    private static final String COL_ID = "prescription_id";
    private static final String COL_DOCTOR_ID = "doctor_id";
    private static final String COL_IMAGE = "image_url";
    private static final String COL_DATE = "prescription_date";
    private static final String COL_DESCRIPTION = "prescription_description";

    public static final String CREATE_TABLE = "create table "+TABLE +
            "( "+COL_ID+" integer primary key, "+
            COL_DOCTOR_ID+" integer, "+
            COL_IMAGE+" text,"+
            COL_DATE +" text,"+
            COL_DESCRIPTION +" text);";

    private CareDatabaseHelper careDatabaseHelper;
    private SQLiteDatabase db;

    public PrescriptionDAO(Context context){
        careDatabaseHelper = new CareDatabaseHelper(context);
    }
    public void open() {
        db = careDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        careDatabaseHelper.close();
    }

    public boolean insert(Prescription prescription){

        ContentValues values = new ContentValues();
        putContentValues(values,prescription);

        this.open();
        long id = db.insert(TABLE,COL_DOCTOR_ID,values);
        close();
        if(id > 0){
            return true;
        }
        else {
            return false;
        }
    }

    private void putContentValues(ContentValues values, Prescription prescription) {
        values.put(COL_DOCTOR_ID,prescription.getDoctorId());
        values.put(COL_IMAGE,prescription.getImageUrl());
        values.put(COL_DATE,prescription.getPrescriptionDate());
        values.put(COL_DESCRIPTION,prescription.getPrescriptionDescription());

    }
    public ArrayList<Prescription> getAllPrescription(){
        this.open();
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT "+COL_ID +","+DoctorDAO.DOCTOR_TABLE+"."+DoctorDAO.COLUMN_ID+","+COL_IMAGE+","+COL_DATE+","+
                DoctorDAO.COLUMN_NAME+","+DoctorDAO.COLUMN_SPECIALITY+","+COL_DESCRIPTION+" from "+TABLE + " INNER JOIN "+DoctorDAO.DOCTOR_TABLE
                +" ON "+TABLE +"."+COL_DOCTOR_ID+" = "+DoctorDAO.DOCTOR_TABLE+"."+DoctorDAO.COLUMN_ID;

        Cursor cursor = db.rawQuery(
                query,
                null
        );

        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i =0;i<cursor.getCount();i++){
                Prescription objPrescription = new Prescription();
                Doctor objDoctor = new Doctor();
                objPrescription.setId(cursor.getInt(0));
                objPrescription.setDoctorId(cursor.getInt(1));
                objPrescription.setImageUrl(cursor.getString(2));
                objPrescription.setPrescriptionDate(cursor.getString(3));
                objDoctor.setId(cursor.getInt(1));
                objDoctor.setName(cursor.getString(4));
                objDoctor.setSpeciality(cursor.getString(5));
                objPrescription.setPrescriptionDescription(cursor.getString(6));
                objPrescription.setDoctor(objDoctor);
                prescriptions.add(objPrescription);
                 cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return prescriptions;
    }
    public ArrayList<Prescription> getselecteddoctorsprescription(int Doctor_id){
        this.open();
        ArrayList<Prescription> prescriptions = new ArrayList<>();

        String query = "SELECT "+COL_ID +","+DoctorDAO.DOCTOR_TABLE+"."+DoctorDAO.COLUMN_ID+","+COL_IMAGE+","+COL_DATE+","+
                DoctorDAO.COLUMN_NAME+","+DoctorDAO.COLUMN_SPECIALITY+","+COL_DESCRIPTION+" from "+TABLE + " INNER JOIN "+DoctorDAO.DOCTOR_TABLE
                +" WHERE "+TABLE +"."+COL_DOCTOR_ID+" = "+DoctorDAO.DOCTOR_TABLE+"."+DoctorDAO.COLUMN_ID+" AND "+DoctorDAO.DOCTOR_TABLE+"."+DoctorDAO.COLUMN_ID+"="+Doctor_id+"";

        Cursor cursor = db.rawQuery(
                query,
                null
        );
        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i =0;i<cursor.getCount();i++){
                Prescription objPrescription = new Prescription();
                Doctor objDoctor = new Doctor();
                objPrescription.setId(cursor.getInt(0));
                objPrescription.setDoctorId(cursor.getInt(1));
                objPrescription.setImageUrl(cursor.getString(2));
                objPrescription.setPrescriptionDate(cursor.getString(3));
                objDoctor.setId(cursor.getInt(1));
                objDoctor.setName(cursor.getString(4));
                objDoctor.setSpeciality(cursor.getString(5));
                objPrescription.setPrescriptionDescription(cursor.getString(6));
                objPrescription.setDoctor(objDoctor);
                prescriptions.add(objPrescription);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();

        return prescriptions;
    }
    public boolean deleteprescription(int id) {
        this.open();
        int deleteId = db.delete(TABLE, COL_ID + " = ?", new String[]{Integer.toString(id)});
        this.close();
        if (deleteId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
