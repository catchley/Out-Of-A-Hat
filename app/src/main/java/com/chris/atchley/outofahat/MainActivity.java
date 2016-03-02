package com.chris.atchley.outofahat;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.atchley.outofahat.adapters.NameAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.chris.atchley.outofahat.preferences";
    public Button addNameButton;
    public EditText nameEditText;
    public TextView nameTextView;
    public Button drawNameButton;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


        Set<String> set = mSharedPreferences.getStringSet(PREFS_FILE, null);
        if (set == null) {
        } else {
            List<String> sample = new ArrayList<String>(set);
            names.clear();
            names.addAll(sample);
        }


        addNameButton = (Button) findViewById(R.id.addNameButton);
        nameEditText = (EditText) findViewById(R.id.addNamEditText);
        nameTextView = (TextView) findViewById(R.id.drawnNameTextView);
        drawNameButton = (Button) findViewById(R.id.drawNameButton);


        final NameAdapter adapter = new NameAdapter(names, this);

        ListView lview = (ListView) findViewById(R.id.myListView);
        lview.setAdapter(adapter);

        addNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEntry = nameEditText.getText().toString();
                if (nameEntry.matches("")) {
                    Toast.makeText(MainActivity.this, "No Name Entered", Toast.LENGTH_LONG).show();
                } else {
                    names.add(nameEntry);
                    nameEditText.setText("");
                    adapter.notifyDataSetChanged();


                }
            }
        });


        drawNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (names.size() < 1) {
                    nameTextView.setText("");
                    Toast.makeText(MainActivity.this, "No Names In List", Toast.LENGTH_LONG).show();
                } else {
                    Random randomize = new Random();
                    String random = names.get(randomize.nextInt(names.size()));
                    nameTextView.setText(random);
                    names.remove(random);
                    adapter.notifyDataSetChanged();

                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Set<String> set = new HashSet<String>();
        set.addAll(names);
        mEditor.putStringSet(PREFS_FILE, set);
        mEditor.commit();


    }
}
