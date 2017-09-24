package com.bitm.mycare.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alamgir on 04/07/2017.
 */

public class CareDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mycare";
    public static final int DATABASE_VERSION = 2;

    public CareDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DoctorDAO.CREATE_TABLE);
        db.execSQL(PrescriptionDAO.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DoctorDAO.DOCTOR_TABLE);
        db.execSQL("drop table if exists "+PrescriptionDAO.TABLE);
        onCreate(db);
    }
}
