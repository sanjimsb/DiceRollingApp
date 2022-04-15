package com.example.dicerollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner getDiceType;
    Button rollDiceButton;
    EditText customDice;
    Button addDice;
    Button rollDiceTwice;
    TextView rolledNumberInfo;
    List<String> diceTypes;
    List<String> userInputs = new ArrayList<>();
    ArrayAdapter<String> catAdapter;
    public static final String MyPREFERENCES = "UserInput";
    public static final String PREFERENCE_KEY = "userCustomDice";
    SharedPreferences sharedpreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.theme_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                return true;
            case R.id.item2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

        List<String> getStoredData = loadData();

        // checking below if the array list is empty or not
        if (getStoredData != null) {
            userInputs = getStoredData;
            for (int i = 0; i < getStoredData.size(); i++) {
                diceTypes.add(getStoredData.get(i));
            }
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
                if(!diceTypes.contains(customDice.getText().toString()) && !customDice.getText().toString().isEmpty()) {
                    userInputs.add(customDice.getText().toString());
                    saveData();
                    diceTypes.add(customDice.getText().toString());
                    int pos = diceTypes.indexOf(customDice.getText().toString());
                    getDiceType.setSelection(pos);
                }
                break;
            case R.id.rollTwice:
                getCurrentDice = Integer.parseInt(getDiceType.getSelectedItem().toString());
                rolledNumberInfo.setText(String.valueOf(roll(getCurrentDice)) + ", " + String.valueOf(roll(getCurrentDice)));
                break;
        }
    }

    // saves dice added by the users to the shared preference.
    private void saveData() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userInputs);
        editor.putString(PREFERENCE_KEY,json);
        editor.commit();
    }

    private List<String> loadData() {
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString(PREFERENCE_KEY, null);

        // get the type of our array list.
        Type type = new TypeToken<List<String>>() {}.getType();

        // getting data from gson and saving it to array list
        List<String> courseModalArrayList = gson.fromJson(json, type);

        return courseModalArrayList;
    }

    //generates the dice randomly
    public int roll(int getDiceType){
        int getRolledNumber = (int)Math.floor(Math.random() * (getDiceType) + 1);
        return getRolledNumber;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}