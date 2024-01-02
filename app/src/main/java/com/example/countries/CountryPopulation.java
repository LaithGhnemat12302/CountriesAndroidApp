package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class CountryPopulation extends AppCompatActivity {
    private Spinner spinnerCountry, spinnerCity;
    private Button btnGetPopulation;
    private EditText edtPopulation;
    private List<String> countryList = new ArrayList<>();
    private ArrayAdapter<String> countryAdapter;
    private List<String> cityList = new ArrayList<>();
    private ArrayAdapter<String> cityAdapter;
    private String url = "https://countriesnow.space/api/v0.1/countries/population/cities";
    // _________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_population);

        setUpViews();
        fillAdapters();

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                loadCities(countryList.get(position));  // Load cities for the selected country
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnGetPopulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopulation();
            }
        });

        loadCountries();
    }
    // _________________________________________________________________________________________________________
    private void setUpViews() {
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnGetPopulation = findViewById(R.id.getPopulation);
        edtPopulation = findViewById(R.id.edtPopulation);
    }
    // _________________________________________________________________________________________________________
    private void fillAdapters() {
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }
    // _________________________________________________________________________________________________________
    private void loadCountries() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray citiesArray = jsonObj.getJSONArray("data");

                            for (int i = 0; i < citiesArray.length(); i++) {
                                JSONObject cityObj = citiesArray.getJSONObject(i);
                                String countryName = cityObj.getString("country");

                                if (!countryList.contains(countryName))
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
    private void loadCities(String selectedCountry) {
        cityList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray citiesArray = jsonObj.getJSONArray("data");

                            for (int i = 0; i < citiesArray.length(); i++) {
                                JSONObject cityObj = citiesArray.getJSONObject(i);
                                String countryName = cityObj.getString("country");
                                String cityName = cityObj.getString("city");

                                if (countryName.equalsIgnoreCase(selectedCountry))
                                    cityList.add(cityName);
                            }

                            cityAdapter.notifyDataSetChanged(); // Update the city spinner
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
    private void getPopulation() {
        final String country = spinnerCountry.getSelectedItem().toString().trim();
        final String city = spinnerCity.getSelectedItem().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray citiesArray = jsonObj.getJSONArray("data");

                            for (int i = 0; i < citiesArray.length(); i++) {
                                JSONObject cityObj = citiesArray.getJSONObject(i);
                                String cityName = cityObj.getString("city");
                                String countryName = cityObj.getString("country");

                                if (cityName.equalsIgnoreCase(city) && countryName.equalsIgnoreCase(country)) {
                                    JSONArray populationCounts = cityObj.getJSONArray("populationCounts");
                                    String population = "Data not available for the years 2010, 2011, 2012, 2013 and 2014";

                                    for (int j = 0; j < populationCounts.length(); j++) {
                                        JSONObject populationObj = populationCounts.getJSONObject(j);
                                        int year = populationObj.getInt("year");

                                        if (year == 2010) {
                                            population = populationObj.getString("value");
                                            break;
                                        }
                                        if (year == 2011) {
                                            population = populationObj.getString("value");
                                            break;
                                        }
                                        if (year == 2012) {
                                            population = populationObj.getString("value");
                                            break;
                                        }
                                        if (year == 2013) {
                                            population = populationObj.getString("value");
                                            break;
                                        }
                                        if (year == 2014) {
                                            population = populationObj.getString("value");
                                            break;
                                        }
                                    }

                                    edtPopulation.setText(population);
                                    return;
                                }
                            }

                            edtPopulation.setText("Not found");


                        } catch (Exception e) {
                            e.printStackTrace();
                            edtPopulation.setText("Error parsing the data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        edtPopulation.setText("Error retrieving the data");
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
