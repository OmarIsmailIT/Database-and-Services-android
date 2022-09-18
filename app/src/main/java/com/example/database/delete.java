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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class delete extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText deleteId;
    private Button d;
    private static final  String ChannelID= " My first Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent i = new Intent (this,MyIntentService.class);
        i.setAction ( MainActivity.KEY_OPEN );
        startService (i);

        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.setItemIconTintList(null);
        nav.setOnNavigationItemSelectedListener(this);

        deleteId = findViewById(R.id.deleteId);
        d = findViewById(R.id.butt);

        d.setOnClickListener(this);
        deleteId.setKeyListener(DigitsKeyListener.getInstance("0123456789"));




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
    private int NoteId =2;
    @Override
    public void onClick(View v) {
        boolean exist = false;
            if(v.getId()==R.id.butt) {

                if (deleteId.getText().toString().isEmpty()){
                    Toast.makeText(this,"Enter A ID",Toast.LENGTH_LONG).show();
                } else {
                    Cursor rs = data.db.rawQuery("select * from emp ;", null);
                    while (rs.moveToNext()) {
                        if (rs.getInt(1) == Integer.parseInt(deleteId.getText().toString())) {
                            exist = true;
                        }
                    }

                    if (exist == true) {

                        Intent i = new Intent (this,MyIntentService.class);
                        i.setAction ( MainActivity.DO_DELETE );
                        i.putExtra ( MainActivity.KEY_VALUE2,deleteId.getText ().toString () );
                        startService (i);
                        NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
                        NotificationCompat.Builder  note=null;

                        createChannel();
                        note = new NotificationCompat.Builder ( this,ChannelID )
                                .setAutoCancel ( true )
                                .setOngoing ( false )
                                .setContentTitle ( "Delete" )
                                .setContentText ( "Delete process has been successfully" )
                                .setColor ( Color.RED  )
                                .setColorized ( true )
                                .setShowWhen ( true )
                                .setSmallIcon ( R.drawable.ic_baseline_delete_24)
                                .setPriority ( NotificationManager.IMPORTANCE_HIGH );
                        man.notify (NoteId, note.build ());
                        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
                        deleteId.setText("");

                    } else if (exist == false) {
                        Toast.makeText(this, "This ID Is Not Exist.....", Toast.LENGTH_LONG).show();
                    }
                    exist = false;
                }

            }
}

}


