package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class search extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private EditText searchId;
    private Button search;
    private TextView show;

    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent i = new Intent (this,MyIntentService.class);
        i.setAction ( MainActivity.KEY_OPEN );
        startService (i);


        BottomNavigationView nav=findViewById ( R.id.nav1 );
        nav.setItemIconTintList ( null );
        nav.setOnNavigationItemSelectedListener (this);

        searchId=findViewById(R.id.searchId);
        search=findViewById(R.id.buttser);
        show=findViewById(R.id.text1);

        search.setOnClickListener(this);
        searchId.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        BroadcastReceiver broad= new BroadcastReceiver () {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction ().equals (MainActivity.RESULT_SEARCH_BACK )) {

                    int exist=intent.getIntExtra(MainActivity.KEY_TOAST,0);
                    if (exist==1) {
                        String Name = intent.getStringExtra(MainActivity.KEY_VALUE1);
                        int Id = intent.getIntExtra(MainActivity.KEY_VALUE2, 12345);
                        String Sex = intent.getStringExtra(MainActivity.KEY_VALUE3);
                        float Base = intent.getFloatExtra(MainActivity.KEY_VALUE4, 12345);
                        float Total = intent.getFloatExtra(MainActivity.KEY_VALUE5, 12345);
                        float Rate = intent.getFloatExtra(MainActivity.KEY_VALUE6, 12345);
                        show.setText((Html.fromHtml("Name=" + Name
                                + "<br>" + "ID=" + Id
                                + "<br>" + "Sex=" + Sex
                                + "<br>" + "Base Salary=" + Base
                                + "<br>" + "Total Sales=" + Total
                                + "<br>" + "Commission Rate=" + Rate)));

                    }
                    else if (exist==2){
                        Toast.makeText(getApplicationContext(), "This ID Is Not Exist..Enter another id", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        IntentFilter y= new IntentFilter ();

        y.addAction ( MainActivity.RESULT_SEARCH_BACK );
        registerReceiver ( broad,  y);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home){
            Intent i  = new Intent ( this, MainActivity.class);
            startActivity ( i );
        }
        return false;
    }
    private void createChannel()
    {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel x;
            x=new NotificationChannel (ChannelID,"My  Hi Channel with you"  , NotificationManager.IMPORTANCE_HIGH);

            NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );

            man.createNotificationChannel ( x );


        }



    }
    private int NoteId =3;
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttser) {


            if (searchId.getText().toString().isEmpty()||searchId.getText().toString().equals(" ")){
                Toast.makeText(this,"Enter A ID",Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent (this,MyIntentService.class);
                i.setAction ( MainActivity.DO_SEARCH );
                i.putExtra ( MainActivity.KEY_VALUE2,searchId.getText ().toString () );
                startService (i);
                NotificationManager man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
                NotificationCompat.Builder  note=null;

                createChannel();
                note = new NotificationCompat.Builder ( this,ChannelID )
                        .setAutoCancel ( true )
                        .setOngoing ( false )
                        .setContentTitle ( "Search" )
                        .setContentText ( "Search process has been successfully" )
                        .setColor ( Color.RED  )
                        .setColorized ( true )
                        .setShowWhen ( true )
                        .setSmallIcon ( R.drawable.ic_baseline_search_24)
                        .setPriority ( NotificationManager.IMPORTANCE_HIGH );
                man.notify (NoteId, note.build ());
                }
            }
        }
    }
