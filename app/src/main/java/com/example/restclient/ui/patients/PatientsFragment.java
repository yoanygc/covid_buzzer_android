package com.example.restclient.ui.patients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclient.R;
import com.example.restclient.databinding.FragmentPatientsBinding;

import org.business.Patient;
import org.business.PatientsRecyclerViewAdapter;

import java.util.ArrayList;

public class PatientsFragment extends Fragment {

    private PatientsViewModel patientsViewModel;
    private FragmentPatientsBinding binding;
    private ArrayList<Patient> patients;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patientsViewModel =
                new ViewModelProvider(this).get(PatientsViewModel.class);

        binding = FragmentPatientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        View view = inflater.inflate(R.layout.fragment_patients, container,false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        setupPatients();
        PatientsRecyclerViewAdapter patientsAdapter = new PatientsRecyclerViewAdapter(root.getContext(), patients);
        recyclerView.setAdapter(patientsAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));





        patientsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setupPatients() {
        String[] patientNames = getResources().getStringArray(R.array.patient_names_txt);
        String[] patientAges = getResources().getStringArray(R.array.patient_ages_txt);
        String[] patientGenders = getResources().getStringArray(R.array.patient_genders_txt);

        patients = new ArrayList<>();

        for (int i = 0; i < patientNames.length; i++) {
            patients.add(new Patient(patientNames[i], patientGenders[i], Integer.valueOf(patientAges[i])));
        }
    }

}