package com.example.skhanna1.countbook2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skhanna1 on 9/27/17.
 */

public class Counter {

    String nameOfCounter;
    Date date;
    String currentValue;
    String initValue;
    String comment;

    public Counter(String name, String iVal, String Com){
        this.nameOfCounter = name;
        this.date = new Date();
        this.initValue = iVal;
        this.comment = Com;
        this.currentValue = iVal;
    }

    public Counter(String name, String iVal){
        this.nameOfCounter = name;
        this.initValue = iVal;
        this.date = new Date();
        this.comment = "";
        this.currentValue = iVal;
    }

    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public String getNameOfCounter(){return this.nameOfCounter;}

    public String getCurrentValue() { return this.currentValue; }

    public String getInitValue(){ return this.initValue;}

    public String getComment(){ return this.comment; }

    public void setCurrentValue(String value){this.currentValue = value;}

    public void setNameOfCounter(String Name){ this.nameOfCounter = Name;}

    public void setInitValue(String Num){ this.initValue = Num; }

    public void setComment(String Commenting){ this.comment = Commenting; }
}
