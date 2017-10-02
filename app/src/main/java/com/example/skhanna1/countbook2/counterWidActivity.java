package com.example.skhanna1.countbook2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class counterWidActivity extends AppCompatActivity {


    Button buttonAdd;
    Button buttonSub;
    EditText Name;
    EditText counter2;
    EditText comment;
    TextView CDate;
    Button buttonReset;
    Button buttonDelete;
    Counter counter3;
    ArrayAdapter<Counter> counterAdapt;
    Button save;
    EditText cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_wid);

        buttonAdd = (Button) findViewById(R.id.button4);
        buttonSub = (Button) findViewById(R.id.button3);
        buttonReset = (Button) findViewById(R.id.button5);
        //buttonDelete = (Button) findViewById(R.id.button6);


        Name = (EditText) findViewById(R.id.naming);
        counter2 = (EditText) findViewById(R.id.editText2);
        comment = (EditText) findViewById(R.id.editText6);
        CDate = (TextView) findViewById(R.id.textView);

        final Intent intent2 = getIntent();

        Gson gson = new Gson();
        String object = intent2.getStringExtra(ListActivity.DateOfPos);
        Type listType = new TypeToken<Counter>() {
        }.getType();

        //position of object
        counter3 = gson.fromJson(object, listType);

        CDate.setText(counter3.getDate());

        String Cname = intent2.getStringExtra(ListActivity.NAME);
        //Gets the Initial Value entered by the user
        final String IVal = intent2.getStringExtra(ListActivity.IVal);
        String CComment = intent2.getStringExtra(ListActivity.CCOM);

        Name.setText(Cname);
        //set the counter position to the value entered by user
        counter2.setText(IVal);
        comment.setText(CComment);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gets the value at the position
                String starVal = counter2.getText().toString();
                //changes to integer
                int intStarVal = Integer.parseInt(starVal);
                //changes its value
                intStarVal++;
                counter2.setText(String.valueOf(intStarVal));
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String starVal2 = counter2.getText().toString();
                int intStarVal2 = Integer.parseInt(starVal2);
                if (intStarVal2 > 0) {
                    intStarVal2--;
                    counter2.setText(String.valueOf(intStarVal2));
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                counter2.setText(String.valueOf(IVal));
            }
        });

        Name.addTextChangedListener(textWatcher);
        counter2.addTextChangedListener(textWatcher);
        comment.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            counter3.setNameOfCounter(String.valueOf(Name.getText()));
            counter3.setCurrentValue(String.valueOf(counter2.getText()));
            counter3.setComment(String.valueOf(comment.getText()));

        }
    };

}
