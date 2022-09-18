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
import android.text.Html;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;

public class display extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final   ArrayList<ExampleItem> examplelist =new ArrayList<>();
    private static final  String ChannelID= " My first Channel";
    private int NoteId =5;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        BroadcastReceiver broad1= new BroadcastReceiver () {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction ().equals (MainActivity.RESULT_DISPLAY_BACK )) {
                    mAdapter = new ExampleAdapter(examplelist);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        };
        IntentFilter y1= new IntentFilter ();

        y1.addAction ( MainActivity.RESULT_DISPLAY_BACK );
        registerReceiver ( broad1,  y1);
        examplelist.clear();

        BottomNavigationView nav=findViewById ( R.id.nav1 );
        nav.setItemIconTintList ( null );
        nav.setOnNavigationItemSelectedListener (this);

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);


        Intent y = new Intent (this,MyIntentService.class);
        y.setAction ( MainActivity.DO_DISPLAY );
        startService (y);

        NotificationManager man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
        NotificationCompat.Builder  note=null;

        createChannel();
        note = new NotificationCompat.Builder ( this,ChannelID )
                .setAutoCancel ( true )
                .setOngoing ( false )
                .setContentTitle ( "Modify" )
                .setContentText ( "Modify process has been successfully" )
                .setColor ( Color.RED  )
                .setColorized ( true )
                .setShowWhen ( true )
                .setSmallIcon ( R.drawable.ic_baseline_update_24 )
                .setPriority ( NotificationManager.IMPORTANCE_HIGH );
        man.notify (NoteId, note.build ());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home){
            Intent i  = new Intent ( this, MainActivity.class);
            startActivity ( i );
        }
        return false;
    }

}