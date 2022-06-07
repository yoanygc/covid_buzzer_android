package com.example.restclient.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import com.example.restclient.R;
import com.example.restclient.business.PatientStatus;
import com.example.restclient.databinding.FragmentPatientsBinding;

import com.example.restclient.business.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PatientsFragment extends Fragment implements PatientsRecyclerViewInterface {

    private PatientsViewModel patientsViewModel;
    private FragmentPatientsBinding binding;
    private ArrayList<Patient> patients;
    private RecyclerView recyclerView;
    TextView tmpJSONTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patientsViewModel =
                new ViewModelProvider(this).get(PatientsViewModel.class);

        binding = FragmentPatientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView = root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        patients = new ArrayList<Patient>();
        PatientsRecyclerViewAdapter patientsAdapter = new PatientsRecyclerViewAdapter(this,
                root.getContext(),
                patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //setupPatients();

        // Read all the Patients registered
        AsyncHttpClient clientAPI = new AsyncHttpClient(true, 80, 443);
        String url = getResources().getString(R.string.base_api_URL) +
                getResources().getString(R.string.patients_api_endpoint);

        clientAPI.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    // Read the array of elements from patients
                    //JSONArray patientsJSONArray = response.getJSONArray("");

                    // Iterating over all items
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);
                        // fullName
                        Integer id = object.getInt("id");
                        String fullName = object.getString("fullName");
                        String gender = object.getString("gender");
                        Integer age = object.getInt("age");

                        JSONObject statusObject = object.getJSONObject("patientStatus");
                        patients.add(new Patient(id,
                                fullName,
                                gender,
                                age,
                                new PatientStatus(statusObject.getInt("id"),
                                        statusObject.getString("status"))));
                    }

                    recyclerView.setAdapter(patientsAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        FloatingActionButton addPatientFloatingActionButton = root.findViewById(R.id.addPatientFloatingActionButton);
        addPatientFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            // Add new Patient. Open
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getApplicationContext(),"onClick FloatActionButton OK!!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewPatientActivity.class);
                startActivity(intent);

            }
        });


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


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "onClick OK!!", Toast.LENGTH_SHORT).show();
    }

}