package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class modify extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private EditText modId, modName, modBase, modTotal, modRate;
    private RadioButton mal,fem;
    private Button ok;
    private Button modify;
    private RadioGroup b;

    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent i = new Intent (this,MyIntentService.class);
        i.setAction ( MainActivity.KEY_OPEN );
        startService (i);

        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.setItemIconTintList(null);
        nav.setOnNavigationItemSelectedListener(this);

        modId = findViewById(R.id.modId);
        modName = findViewById(R.id.modName);
        modBase = findViewById(R.id.modBase);
        modTotal = findViewById(R.id.modTotal);
        modRate = findViewById(R.id.modRate);
        mal = findViewById(R.id.mal1);
        fem = findViewById(R.id.fem2);
        b = findViewById(R.id.radioGroup5);
        ok = findViewById(R.id.ok);
        modify = findViewById(R.id.modify);


        ok.setOnClickListener(this);
        mal.setOnClickListener(this);
        fem.setOnClickListener(this);
        modify.setOnClickListener(this);



        modName.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        modBase.setVisibility(View.INVISIBLE);
        modTotal.setVisibility(View.INVISIBLE);
        modRate.setVisibility(View.INVISIBLE);
        modify.setVisibility(View.INVISIBLE);

        modName.setKeyListener(DigitsKeyListener.getInstance("asdfghjklpoiuytrewqzxcvbnmASDFGHJKLPOIUYTREWQZXCVBNM"));
        modId.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        modBase.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        modTotal.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        modRate.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
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
    private int NoteId =4;
    @Override
    public void onClick(View v) {
         int exist = 0;
        if (v.getId() == R.id.ok) {
            if (modId.getText().toString().isEmpty() || modId.getText().toString().equals(" ")) {
                Toast.makeText(this, " Enter A ID", Toast.LENGTH_LONG).show();
            }
            else {
                Cursor rs = data.db.rawQuery("select *  from emp  where id = ? ;",
                        new String[]{modId.getText().toString()});
                while (rs.moveToNext()) {
                    if (rs.getInt(1) == Integer.parseInt(modId.getText().toString())) {
                        exist = 1;
                        modName.setText(rs.getString(0).toString());
                        if (rs.getString(2).equals("Male")) {
                            mal.setChecked(true);
                            fem.setChecked(false);
                        }
                        if (rs.getString(2).equals("Female")) {
                            fem.setChecked(true);
                            mal.setChecked(false);
                        }
                        modBase.setText(rs.getString(3).toString());
                        modTotal.setText(rs.getString(4).toString());
                        modRate.setText(rs.getString(5).toString());
                    }
                }

                if (exist == 1) {

                    modName.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    modBase.setVisibility(View.VISIBLE);
                    modTotal.setVisibility(View.VISIBLE);
                    modRate.setVisibility(View.VISIBLE);
                    modify.setVisibility(View.VISIBLE);

                }
                if (exist == 0)
                    Toast.makeText(this, "This ID Is Not Exist", Toast.LENGTH_LONG).show();

            }
        }
            if (v.getId() == R.id.modify) {

                Intent i = new Intent (this,MyIntentService.class);
                if (mal.isChecked()==true) {

                    i.setAction ( MainActivity.DO_MODIFY );
                    i.putExtra ( MainActivity.KEY_VALUE1,modName.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE2,modId.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE3,"Male");
                    i.putExtra ( MainActivity.KEY_VALUE4,modBase.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE5,modTotal.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE6,modRate.getText ().toString () );
                    startService (i);
                    Toast.makeText(this,"Modified",Toast.LENGTH_SHORT).show();
                }
                else if (fem.isChecked()==true ){

                    i.setAction ( MainActivity.DO_MODIFY );
                    i.putExtra ( MainActivity.KEY_VALUE1,modName.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE2,modId.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE3,"Female");
                    i.putExtra ( MainActivity.KEY_VALUE4,modBase.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE5,modTotal.getText ().toString () );
                    i.putExtra ( MainActivity.KEY_VALUE6,modRate.getText ().toString () );
                    startService (i);
                    Toast.makeText(this,"Modified",Toast.LENGTH_SHORT).show();

                }
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
    }
}

