package com.example.restclient.ui.patients;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restclient.R;
import com.example.restclient.business.Patient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.MyViewHolder>{

    private final PatientsRecyclerViewInterface recyclerViewInterface;

    private Context context;
    private List<Patient> patients;

    public PatientsRecyclerViewAdapter(PatientsRecyclerViewInterface recyclerViewInterface,
                                       Context context,
                                       List<Patient> patients) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nameTextView.setText(patients.get(position).getFullName());
        holder.ageTextView.setText("Age: " + patients.get(position).getAge().toString());
        holder.genderTextView.setText("Gender: " + patients.get(position).getGender());
        holder.statusTextView.setText("Status: " + patients.get(position).getPatientStatus().getStatus());

    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView;
        private TextView ageTextView;
        private TextView genderTextView;
        private TextView statusTextView;

        public MyViewHolder(@NonNull View itemView, PatientsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.fullNameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }
}
