package com.example.skhanna1.countbook2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    public static final String NAME = "nameOfCounter";
    public static final String IVal = "InitialVal";
    public static final String CCOM = "CounterComment";
    public static String DateOfPos = "position";
    public static final String i = "pos";
    EditText counterName;
    int numOfCounters = 0;
    Button addCounterButton;
    Button del;

    ListView list;
    ArrayList<Counter> counterObjArray;
    //Bridge between UI and data in array
    ArrayAdapter<Counter> counterAdapter;
    Counter newCounter;
    EditText counterComment;
    EditText counterInit;
    String nameOfCounter;
    String InitVal;
    String CComment;
    Button clearButton;
    Integer ipos;
    String strEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //user enters the name of counter and we get it
        counterName = (EditText) findViewById(R.id.naming);
        counterComment = (EditText) findViewById(R.id.editText);
        counterInit = (EditText) findViewById(R.id.editText4);

        //connecting to the add counter button
        addCounterButton = (Button) findViewById(R.id.button);
        clearButton = (Button)findViewById((R.id.clear));

        counterObjArray = new ArrayList<Counter>();

        addCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameOfCounter = counterName.getText().toString();
                InitVal = counterInit.getText().toString();
                CComment = counterComment.getText().toString();
                createList(nameOfCounter, InitVal, CComment);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                counterAdapter.clear();
                counterAdapter.notifyDataSetChanged();
            }
        });

    }

    public void createList(String name, String initialValue, String Comment) {
        newCounter = new Counter(name, initialValue, Comment);
        counterObjArray.add(newCounter);

        saveInFile();

        list = (ListView) findViewById(R.id.counterList);

        counterAdapter = new CustomAdapter(ListActivity.this, R.layout.custom_list, counterObjArray);

        list.setAdapter(counterAdapter);
        counterAdapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, counterWidActivity.class);
                intent.putExtra(NAME, counterObjArray.get(position).getNameOfCounter());
                intent.putExtra(IVal, counterObjArray.get(position).getInitValue());
                intent.putExtra(CCOM, counterObjArray.get(position).getComment());
                intent.putExtra(i, position);

                ipos = position;

                Gson gson = new Gson();
                String obj = gson.toJson(counterObjArray.get(position));
                intent.putExtra(DateOfPos, obj);

                startActivity(intent);

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                Counter item = counterAdapter.getItem(index);
                counterAdapter.remove(item);
                counterAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFile();
        counterAdapter = new ArrayAdapter<Counter>(this, R.layout.custom_list, counterObjArray);
        counterAdapter.notifyDataSetChanged();
    }

    public void saveInFile() {

        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(counterObjArray, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void loadFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterObjArray = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counterObjArray = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                strEditText = data.getStringExtra("editTextValue");
            }
        }
    }
}
