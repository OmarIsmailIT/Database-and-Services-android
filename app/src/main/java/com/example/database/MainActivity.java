package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final  String  DO_INSERT = "insert";
    public static final  String  DO_DELETE = "delete";
    public static final  String  DO_SEARCH = "search";
    public static final  String  DO_MODIFY = "modify";
    public static final  String  DO_DISPLAY = "display";
    public static final  String  KEY_VALUE1= "name";
    public static final  String  KEY_VALUE2= "id";
    public static final  String  KEY_VALUE3= "sex";
    public static final  String  KEY_VALUE4= "base";
    public static final  String  KEY_VALUE5= "total";
    public static final  String  KEY_VALUE6= "rate";
    public static final  String  KEY_OPEN  = "open";
    public static final  String  RESULT_SEARCH_BACK  = "search result";
    public static final  String  RESULT_DISPLAY_BACK  = "display";
    public static final  String  KEY_TOAST  = "t";

    private Button insert ;
    private Button delete ;
    private Button search ;
    private Button modify ;
    private Button display ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert=findViewById(R.id.b1);
        delete=findViewById(R.id.b2);
        search=findViewById(R.id.b3);
        modify=findViewById(R.id.b4);
        display=findViewById(R.id.b5);

        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        search.setOnClickListener(this);
        modify.setOnClickListener(this);
        display.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b1) {
            Intent i = new Intent(this, insert.class);
            startActivity(i);
        } else if (v.getId() == R.id.b2) {
            Intent i = new Intent(this, delete.class);
            startActivity(i);
        } else if (v.getId() == R.id.b3) {
            Intent i = new Intent(this, search.class);
            startActivity(i);
        } else if (v.getId() == R.id.b4) {
            Intent i = new Intent(this, modify.class);
            startActivity(i);
        } else if (v.getId() == R.id.b5) {
            Intent i = new Intent(this, display.class);
            startActivity(i);
        }
    }
}