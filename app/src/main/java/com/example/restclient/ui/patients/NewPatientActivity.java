package com.example.restclient.ui.patients;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.restclient.MainActivity;
import com.example.restclient.R;
import com.example.restclient.business.Company;
import com.example.restclient.business.Patient;
import com.example.restclient.business.PatientStatus;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> companiesStrList;
    ArrayList<Company> companies;
    ArrayList<String> patientStatusStrList;
    ArrayList<PatientStatus> patientStatuses;

    EditText fullNameEditText;
    EditText ageEditText;
    Spinner companiesSpinner;
    Spinner statusSpinner;
    Patient newPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        fullNameEditText = (EditText) findViewById(R.id.fullNameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEeditText);
        companiesSpinner = (Spinner) findViewById(R.id.companiesSpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);

        companiesStrList = new ArrayList<>();
        companies = new ArrayList<>();
        patientStatusStrList = new ArrayList<>();
        newPatient = new Patient();
        patientStatuses = new ArrayList<>();

        ArrayAdapter<CharSequence> companiesSpinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                companiesStrList);
        companiesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> statusSpinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                patientStatusStrList);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // API Call to load all companies in the Spinner
        AsyncHttpClient clientAPI = new AsyncHttpClient(true, 80, 443);
        String url = getResources().getString(R.string.base_api_URL) + getResources().getString(R.string.companies_api_endpoint);
        clientAPI.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    // Read the array of elements from companies
                    //JSONArray patientsJSONArray = response.getJSONArray("");

                    // Iterating over all items
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);

                        Integer id = object.getInt("id");
                        String name = object.getString("name");
                        String phoneNumber = object.getString("phoneNumber");
                        String email = object.getString("email");

                        companies.add(new Company(id, name, phoneNumber, email));
                        companiesStrList.add(name);
                    }

                    companiesSpinner.setAdapter(companiesSpinnerAdapter);
                    newPatient.setCompany(companies.get(0));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        companiesSpinner.setOnItemSelectedListener(this);

        // API Call to load all patients status in the Spinner
        clientAPI = new AsyncHttpClient(true, 80, 443);
        url = getResources().getString(R.string.base_api_URL) + getResources().getString(R.string.patient_status_api_endpoint);
        clientAPI.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    // Read the array of elements from PatientStatus

                    // Iterating over all items
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = (JSONObject) response.get(i);

                        Integer id = object.getInt("id");
                        String status = object.getString("status");

                        patientStatuses.add(new PatientStatus(id, status));
                        patientStatusStrList.add(status);
                    }

                    statusSpinner.setAdapter(statusSpinnerAdapter);
                    newPatient.setPatientStatus(patientStatuses.get(0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        statusSpinner.setOnItemSelectedListener(this);


    }

    public void buttonCancelOnClick(View view) {
        Intent intent = new Intent(NewPatientActivity.this, MainActivity.class);
        startActivity(intent);

    }


    public void buttonAcceptOnClick(View view) {


        newPatient.setFullName(fullNameEditText.getText().toString());
        newPatient.setAge(Integer.valueOf(ageEditText.getText().toString()));


        JSONObject company = new JSONObject();
        JSONObject healthCentre = new JSONObject();
        JSONObject patient = new JSONObject();
        JSONObject patientStatus = new JSONObject();

        try {
            company.put("id", newPatient.getCompany().getId());
            healthCentre.put("id", 1);
            patientStatus.put("id", newPatient.getPatientStatus().getId());
            patient.put("fullName", newPatient.getFullName());
            patient.put("age", newPatient.getAge());
            patient.put("gender", newPatient.getGender());
            patient.put("company", company);
            patient.put("healthCentre", healthCentre);
            patient.put("patientStatus", patientStatus);

        } catch (JSONException e) {
            Log.e("Error. ", e.getMessage());
        }

        // Call API to insert a new Patient
        StringEntity entity = new StringEntity(patient.toString(), "UTF-8");
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        String url = getResources().getString(R.string.base_api_URL) + getResources().getString(R.string.register_patient_api_endpoint);
        AsyncHttpClient clientAPI = new AsyncHttpClient(true, 80, 443);
        clientAPI.post(getApplicationContext(), url, entity, "application/json",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                try {
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                    }

                });

        Intent intent = new Intent(NewPatientActivity.this, MainActivity.class);
        startActivity(intent);

        //Toast.makeText(getApplicationContext(),"onClick Accept!!",Toast.LENGTH_SHORT).show();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    newPatient.setGender("MALE");
                break;
            case R.id.radioButtonFemale:
                if (checked)
                    newPatient.setGender("FEMALE");
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        String selected = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.companiesSpinner){

            if(!companies.isEmpty()){
                Company selectedCompany = null;
                for (Company company : companies) {
                    if (company.getName().equals(selected)) {
                        selectedCompany = company;
                        break;
                    }
                }

                newPatient.setCompany(selectedCompany);
            }
        }else if(parent.getId() == R.id.statusSpinner){
            if(!patientStatuses.isEmpty()){
                PatientStatus selectedStatus = null;
                for (PatientStatus status : patientStatuses) {
                    if (status.getStatus().equals(selected)) {
                        selectedStatus = status;
                        break;
                    }
                }

                newPatient.setPatientStatus(selectedStatus);
            }

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}