package com.example.restclient.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import cz.msebera.android.httpclient.Header;

import com.example.restclient.R;
import com.example.restclient.business.GlobalCovidInfo;
import com.example.restclient.databinding.FragmentHomeBinding;
import com.example.restclient.utils.RestClientUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView populationTextView = binding.populationTextView;
        final TextView confirmedCasesTextView = binding.confirmedCasesTextView;
        final TextView deathsTextView = binding.deathsTextView;
        final TextView updatedAtTextView = binding.updatedAtTextView;
        final TextView newConfirmedCasesTextView = binding.newConfirmedCasesTextView;
        final TextView newDeathsTextView = binding.newDeathsTextView;
        final TextView newRecoveredTextView = binding.newRecoveredTextView;

        // Call Rest API to load Global information
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(getResources().getString(R.string.global_covid_URL), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    //Population
                    String population = dataObject.getString("population");
                    populationTextView.setText(population);

                    // Read the array of elements in the TimeLine
                    JSONArray timeLine = dataObject.getJSONArray("timeline");
                    // Get the last day updated object
                    JSONObject object = (JSONObject) timeLine.get(0);
                    // Updated At:
                    String updated_at = object.getString("date");
                    updatedAtTextView.setText("Updated: " + updated_at);
                    // Confirmed Cases
                    String confirmed = object.getString("confirmed");
                    confirmedCasesTextView.setText(confirmed);
                    // Deaths
                    String deaths = object.getString("deaths");
                    deathsTextView.setText(deaths);

                    // New Confirmed Cases
                    String new_confirmed = object.getString("new_confirmed");
                    newConfirmedCasesTextView.setText(new_confirmed);
                    // New Deaths
                    String new_deaths = object.getString("new_deaths");
                    newDeathsTextView.setText(new_deaths);
                    // New Recovered
                    String new_recovered = object.getString("new_recovered");
                    newRecoveredTextView.setText(new_recovered);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        AsyncHttpClient clientAPI = new AsyncHttpClient(8080);
        String url = getResources().getString(R.string.base_api_URL) + getResources().getString(R.string.hello_api_endpoint);
        clientAPI.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("message");
                    //Hello Message
                    String message = dataObject.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(),
                                message + "  Status Code: " + String.valueOf(statusCode),
                                Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Error...No Connection!!!. Status Code: " + String.valueOf(statusCode),
                        Toast.LENGTH_LONG).show();
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