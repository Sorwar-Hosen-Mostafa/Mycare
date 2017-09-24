package com.bitm.mycare.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitm.mycare.R;
import com.bitm.mycare.model.Doctor;

import java.util.ArrayList;

/**
 * Created by Alamgir on 04/08/2017.
 */

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    Context context;
    ArrayList<Doctor> doctors;
    public DoctorAdapter(@NonNull Context context,ArrayList<Doctor>doctors) {
        super(context, R.layout.single_row_doctor_list,doctors);
        this.context = context;
        this.doctors = doctors;
    }

    private class ViewHolder {
        TextView doctorNameTV;
        TextView doctorSpecialityTV;
        TextView lastvisited;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_row_doctor_list, parent, false);
            viewHolder.doctorNameTV = (TextView) convertView.findViewById(R.id.doctor_name_tv);
            viewHolder.doctorSpecialityTV = (TextView) convertView.findViewById(R.id.doctor_speciality_tv);
            viewHolder.lastvisited = (TextView)convertView.findViewById(R.id.last_visited_tv);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.doctorNameTV.setText(doctors.get(position).getName());
            viewHolder.doctorSpecialityTV.setText(doctors.get(position).getPhone());
            viewHolder.lastvisited.setText(doctors.get(position).getAppointmentDate()+" (last visited)");
        return convertView;
    }
}
