package com.chris.atchley.outofahat;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.atchley.outofahat.adapters.NameAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
    private CheckBox deleteCheckBox;
    public boolean checked;

    ArrayList<String> names = new ArrayList<String>();

     NameAdapter adapter = new NameAdapter(names, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        checked = mSharedPreferences.getBoolean("Checkbox1", false);


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
        deleteCheckBox = (CheckBox) findViewById(R.id.deleteChosenCheckBox);
        deleteCheckBox.setChecked(checked);




        ListView lview = (ListView) findViewById(R.id.myListView);
        lview.setAdapter(adapter);

        addNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEntry = nameEditText.getText().toString();
                if (nameEntry.matches("")) {
                    Toast.makeText(MainActivity.this, "No Name Entered", Toast.LENGTH_LONG).show();
                } else {
                    names.add(0, nameEntry);
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

                    adapter.notifyDataSetChanged();
                    if(deleteCheckBox.isChecked()){
                        names.remove(random);
                    }

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
        checked = deleteCheckBox.isChecked();
        mEditor.putBoolean("Checkbox1", checked);
        mEditor.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_Clear:
                names.clear();
                adapter.notifyDataSetChanged();


                break;
            // action with ID action_settings was selected
            case R.id.action_Extras:

                Intent intent = new Intent(MainActivity.this, ExtrasActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;

    }

}
