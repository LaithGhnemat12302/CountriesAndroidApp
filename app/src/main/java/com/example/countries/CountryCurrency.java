package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryCurrency extends AppCompatActivity {
    private Spinner spinnerCountry;
    private EditText edtCurrency;
    private Button btnGetCurrency;
    private List<String> countryList = new ArrayList<>();
    private ArrayAdapter<String> countryAdapter;
    private String url = "https://countriesnow.space/api/v0.1/countries/currency";
    // _________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_currency);

        setUpView();
        fillAdapters();

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnGetCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCountry = spinnerCountry.getSelectedItem().toString();
                getCurrencyForCountry(selectedCountry);
            }
        });

        loadCountries();
    }
// _________________________________________________________________________________________________________
    private void setUpView() {
        spinnerCountry = findViewById(R.id.spinnerCountry);
        edtCurrency = findViewById(R.id.edtCurrency);
        btnGetCurrency = findViewById(R.id.btnGetCurrency);
    }
    // _________________________________________________________________________________________________________
    private void fillAdapters() {
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);
    }
    // _________________________________________________________________________________________________________
    private void loadCountries() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray countriesArray = jsonObj.getJSONArray("data");

                            for (int i = 0; i < countriesArray.length(); i++) {
                                JSONObject countryObj = countriesArray.getJSONObject(i);
                                String countryName = countryObj.getString("name");
                                countryList.add(countryName);
                            }

                            countryAdapter.notifyDataSetChanged();  // Update the country spinner
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
    // _________________________________________________________________________________________________________
    private void getCurrencyForCountry(String countryName) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray countriesArray = jsonObj.getJSONArray("data");

                            for (int i = 0; i < countriesArray.length(); i++) {
                                JSONObject countryObj = countriesArray.getJSONObject(i);
                                String name = countryObj.getString("name");

                                if (name.equalsIgnoreCase(countryName)) {
                                    String currency = countryObj.getString("currency");
                                    edtCurrency.setText(currency);
                                    return;
                                }
                            }

                            edtCurrency.setText("Currency not available");  // If the country is not found
                        } catch (Exception e) {
                            edtCurrency.setText("Error parsing the data");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        edtCurrency.setText("Error retrieving the data");
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}

