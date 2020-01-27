package com.example.properties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String ALCOHOLS_FILENAME = "alcohols.xml";
    private static final String MIXERS_FILENAME = "mixers.xml";
    Properties alcohols;
    Properties mixers;
    Spinner alcoholSP;
    Spinner mixerSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alcoholSP = findViewById(R.id.alcoholSP);
        mixerSP = findViewById(R.id.mixerSP);
        alcohols = new Properties();
        mixers = new Properties();
        loadIngredients();
        fillSpinners();
    }

    private void loadIngredients() {
        try {
            FileInputStream fis = openFileInput(ALCOHOLS_FILENAME);
            alcohols.loadFromXML(fis);
            fis.close();
            FileInputStream fis2 = openFileInput(MIXERS_FILENAME);
            mixers.loadFromXML(fis2);
            fis2.close();
        } catch (FileNotFoundException fnfe) {
            alcohols.setProperty("A1", "Ron");
            alcohols.setProperty("A2", "Vodka");
            alcohols.setProperty("A3", "Mezcal");
            alcohols.setProperty("A4", "Vodka");
            alcohols.setProperty("A5", "Whiskey");
            mixers.setProperty("M1", "Coca Cola");
            mixers.setProperty("M2", "Agua Mineral");
            mixers.setProperty("M3", "Jugo de naranja");
            mixers.setProperty("M4", "Sprite");
            mixers.setProperty("M5", "Jugo de limon");
            try {
                FileOutputStream fos = openFileOutput(ALCOHOLS_FILENAME, Context.MODE_PRIVATE);
                alcohols.storeToXML(fos, null);
                fos.close();
                FileOutputStream fos2 = openFileOutput(MIXERS_FILENAME, Context.MODE_PRIVATE);
                mixers.storeToXML(fos2, null);
                fos2.close();
            } catch (IOException ioe) {
                Toast.makeText(this, "No se puede guardar los ingredientes.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException ioe) {
            Toast.makeText(this, "No se puede leer los ingredientes.", Toast.LENGTH_LONG).show();
        }
    }

    private void fillSpinners(){
        List<String> alcoholArray = new ArrayList<>();
        alcoholArray.addAll((Collection<String>)(Collection<?>)alcohols.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alcoholArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcoholSP.setAdapter(adapter);

        List<String> mixersArray = new ArrayList<>();
        mixersArray.addAll((Collection<String>)(Collection<?>)mixers.values());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mixersArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mixerSP.setAdapter(adapter2);
    }
}
