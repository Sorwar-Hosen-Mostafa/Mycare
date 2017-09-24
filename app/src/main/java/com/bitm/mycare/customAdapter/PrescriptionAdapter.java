package com.bitm.mycare.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitm.mycare.R;
import com.bitm.mycare.model.Prescription;

import java.util.ArrayList;

/**
 * Created by Alamgir on 04/08/2017.
 */

public class PrescriptionAdapter extends ArrayAdapter<Prescription> {
    private Context context;
    private ArrayList<Prescription> prescriptions;
    public PrescriptionAdapter(@NonNull Context context, ArrayList<Prescription> prescriptions) {
        super(context, R.layout.prescription_list_row,prescriptions);
        this.context = context;
        this.prescriptions = prescriptions;
    }
    private class ViewHolder {
        ImageView imageView;
        TextView nameTV;
        TextView dateTV;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prescription_list_row, parent, false);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.row_name_tv);
            viewHolder.dateTV = (TextView) convertView.findViewById(R.id.row_date_tv);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.prescription_image);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTV.setText(prescriptions.get(position).getDoctor().getName());
        viewHolder.dateTV.setText(prescriptions.get(position).getPrescriptionDate());
        if(prescriptions.get(position).getImageUrl()!=null){
            Imageprocess.setPic(prescriptions.get(position).getImageUrl(),viewHolder.imageView);
        }


        return convertView;
    }
}
