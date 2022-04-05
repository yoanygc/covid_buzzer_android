package com.example.restclient.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.restclient.RestClientUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import cz.msebera.android.httpclient.Header;

import com.example.restclient.R;
import com.example.restclient.databinding.FragmentHomeBinding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private String globalDataCovidURL = "https://corona-api.com/countries/LC";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView footText = binding.footText;
        final TextView populationText = binding.populationTextView;
        final TextView confirmedCasesTextView = binding.confirmedCasesTextView;
        final TextView deathsTextView = binding.deathsTextView;
        final TextView updatedAtTextView = binding.updatedAtTextView;




        // Call Rest API to load Global information
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(globalDataCovidURL,null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONObject dataObject = response.getJSONObject("data");
                    //Population
                    String population = dataObject.getString("population");
                    populationText.setText(population);

                    // Read the array of elements in the TimeLine
                    JSONArray timeLine = dataObject.getJSONArray("timeline");

                    // Get the last day updated object
                    JSONObject object = (JSONObject) timeLine.get(0);
                    // Updated At:
                    String updated_at= object.getString("updated_at");
                    updatedAtTextView.setText(updated_at);

                    // Confirmed Cases
                    String confirmed = object.getString("confirmed");
                    confirmedCasesTextView.setText(confirmed);
                    // Deaths
                    String deaths = object.getString("deaths");
                    deathsTextView.setText(deaths);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });




//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//            }
//        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}