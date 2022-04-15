package com.example.dicerollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner getDiceType;
    Button rollDiceButton;
    EditText customDice;
    Button addDice;
    Button rollDiceTwice;
    TextView rolledNumberInfo;
    List<String> diceTypes;
    ArrayAdapter<String> catAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollDiceButton = findViewById(R.id.rollDice);
        rollDiceTwice = findViewById(R.id.rollTwice);
        addDice = findViewById(R.id.addCustomDice);
        getDiceType = (Spinner) findViewById(R.id.diceType);
        customDice = (EditText) findViewById(R.id.customDice);
        rolledNumberInfo = (TextView) findViewById(R.id.rolledNumber);
        List<String> defaultDice = Arrays.asList("4","6","8","10","12","20");
        diceTypes = new ArrayList<String>();

        catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diceTypes);
        getDiceType.setAdapter(catAdapter);
        for(int i=0; i < defaultDice.size(); i++) {
            diceTypes.add(defaultDice.get(i));
        }
        catAdapter.notifyDataSetChanged();

        rollDiceButton.setOnClickListener(this);
        rollDiceTwice.setOnClickListener(this);
        addDice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int getCurrentDice;
        switch(view.getId()) {
            case R.id.rollDice:
                getCurrentDice = Integer.parseInt(getDiceType.getSelectedItem().toString());
                System.out.println(roll(getCurrentDice));
                rolledNumberInfo.setText(String.valueOf(roll(getCurrentDice)));
                break;
            case R.id.addCustomDice:
                System.out.println(customDice.getText());
                if(!diceTypes.contains(customDice.getText().toString())) {
                    diceTypes.add(customDice.getText().toString());
                    int pos = diceTypes.indexOf(customDice.getText().toString());
                    getDiceType.setSelection(pos);
                }
//                getCurrentDice = Integer.parseInt(getDiceType.getSelectedItem().toString());
                break;
            case R.id.rollTwice:
                getCurrentDice = Integer.parseInt(getDiceType.getSelectedItem().toString());
                rolledNumberInfo.setText(String.valueOf(roll(getCurrentDice)) + ", " + String.valueOf(roll(getCurrentDice)));
                break;
        }
    }

    public int roll(int getDiceType){
        int getRolledNumber = (int)Math.floor(Math.random() * (getDiceType) + 1);
        return getRolledNumber;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}