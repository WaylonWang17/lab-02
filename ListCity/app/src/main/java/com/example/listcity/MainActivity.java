package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //field declarations
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCity, deleteCity, confirmInput;
    String selectedCity = null;
    EditText cityInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //connects to ui variables from activity_main.xml
        cityList = findViewById(R.id.city_list);
        addCity = findViewById(R.id.addCity);
        deleteCity = findViewById(R.id.deleteCity);
        confirmInput = findViewById(R.id.Confirm);
        cityInput = findViewById(R.id.NameText);

        //adds cities
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityInput.setVisibility(View.VISIBLE);
                confirmInput.setVisibility(View.VISIBLE);

                //confirm input for adding cities
                confirmInput.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //converts input to string and adds it to the array
                        String str = cityInput.getText().toString();
                        if (!str.isEmpty()){
                            dataList.add(str);
                            cityAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, str + " added!", Toast.LENGTH_SHORT).show();
                            cityInput.setText("");
                            cityInput.setVisibility(View.INVISIBLE);
                            confirmInput.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        //select city to delete
        //parent = whole listview, view = specific row, position=index for that row
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            cityInput.setVisibility(View.INVISIBLE);
            confirmInput.setVisibility(View.INVISIBLE);
            selectedCity = dataList.get(position);
            Toast.makeText(MainActivity.this, selectedCity + " selected!", Toast.LENGTH_SHORT).show(); //shows city selected popup
        });

        //delete the city
        deleteCity.setOnClickListener(v -> {
            //makes sure the city is there and not null
            if (selectedCity != null && dataList.contains(selectedCity)) {
                dataList.remove(selectedCity);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, selectedCity + " deleted!", Toast.LENGTH_SHORT).show();
                selectedCity = null; //resets back to nothing selected
            } else {
                Toast.makeText(MainActivity.this, "no city selected!", Toast.LENGTH_SHORT).show();
            }
        });

        String []cities = {"Edmonton", "Vancouver", "Burnaby", "Sydney", "Moscow", "Tokyo", "Beijing", "San Francisco"};

        dataList = new ArrayList<>(); //creates empty array
        dataList.addAll(Arrays.asList(cities)); //copies all cities into array
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList); //connect ui to datalist array
        cityList.setAdapter(cityAdapter); //shows all rows of array

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

