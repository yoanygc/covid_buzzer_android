package org.business;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restclient.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<Patient> patients;

    public PatientsRecyclerViewAdapter(Context context, List<Patient> patients) {
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);


        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nameTextView.setText(patients.get(position).getFullName());
        holder.ageTextView.setText("Age: " + patients.get(position).getAge().toString());
        holder.genderTextView.setText("Gender: " + patients.get(position).getGender());


    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView;
        private TextView ageTextView;
        private TextView genderTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.fullNameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);

        }

    }
}
