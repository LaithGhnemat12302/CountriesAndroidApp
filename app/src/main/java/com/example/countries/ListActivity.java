package com.example.countries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView mainListView = (ListView) findViewById(R.id.main_list);
        ArrayAdapter<Item> mainListAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, Item.items);
        mainListView.setAdapter(mainListAdapter);

        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            Intent intent = null;

            if(position == 0)
                intent = new Intent(ListActivity.this, CountryPopulation.class);
            else if(position == 1)
                intent = new Intent(ListActivity.this, CountryCurrency.class);

            startActivity(intent);
        };

        mainListView.setOnItemClickListener(itemClickListener);
    }


}