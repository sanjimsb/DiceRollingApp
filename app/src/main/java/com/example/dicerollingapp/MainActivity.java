package com.example.dicerollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner getDiceType;
    List<String> diceTypes;
    ArrayAdapter<String> catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDiceType = (Spinner) findViewById(R.id.diceType);
        diceTypes = new ArrayList<String>();

        catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diceTypes);
        getDiceType.setAdapter(catAdapter);
        diceTypes.add("1");
        diceTypes.add("2");


    }
}