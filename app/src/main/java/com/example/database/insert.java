package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class insert extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private EditText name, id, base, total, rate;
    private RadioButton radio1;
    private RadioButton radio2;
    private Button ins;

    private static final String ChannelID = " My first Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent i = new Intent(this, MyIntentService.class);
        i.setAction(MainActivity.KEY_OPEN);
        startService(i);

        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.setItemIconTintList(null);
        nav.setOnNavigationItemSelectedListener(this);

        name = findViewById(R.id.e1);
        id = findViewById(R.id.e2);
        radio1 = findViewById(R.id.r1);
        radio2 = findViewById(R.id.r2);
        base = findViewById(R.id.e4);
        total = findViewById(R.id.e5);
        rate = findViewById(R.id.e6);
        ins = findViewById(R.id.button1);

        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        ins.setOnClickListener(this);


        id.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        base.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        total.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        rate.setKeyListener(DigitsKeyListener.getInstance("0123456789."));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        return false;
    }

    private void createChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel x;
            x = new NotificationChannel(ChannelID, "My  Hi Channel with you", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager man = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            man.createNotificationChannel(x);


        }


    }

    private int NoteId = 1;

    @Override
    public void onClick(View v) {
        boolean exist = false;
        if (v.getId() == R.id.button1) {

            if (name.getText().toString().isEmpty() || name.getText().toString().equals("")
                    && id.getText().toString().isEmpty() || id.getText().toString().equals("")
                    && radio1.isChecked() == false && radio2.isChecked() == false
                    && base.getText().toString().isEmpty() || base.getText().toString().equals("")
                    && total.getText().toString().isEmpty() || total.getText().toString().equals("")
                    && rate.getText().toString().isEmpty() || rate.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter A Information", Toast.LENGTH_LONG).show();
            } else if (name.getText().toString().isEmpty()
                    || name.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter  The Name", Toast.LENGTH_LONG).show();
            } else if (id.getText().toString().isEmpty()
                    || id.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter The ID", Toast.LENGTH_LONG).show();
            } else if (radio1.isChecked() == false && radio2.isChecked() == false) {
                Toast.makeText(this, "Please Enter The Sex", Toast.LENGTH_LONG).show();
            } else if (base.getText().toString().isEmpty()
                    || base.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter The Base Salary", Toast.LENGTH_LONG).show();
            } else if (total.getText().toString().isEmpty()
                    || total.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter The Total Sales", Toast.LENGTH_LONG).show();
            } else if (rate.getText().toString().isEmpty()
                    || rate.getText().toString().equals("")) {
                Toast.makeText(this, "Please Enter The Commission Rate", Toast.LENGTH_LONG).show();
            } else {


                Cursor rs = data.db.rawQuery("select * from emp ;", null);
                while (rs.moveToNext()) {
                    if (rs.getInt(1) == Integer.parseInt(id.getText().toString())) {
                        exist = true;
                    }
                }

                if (exist == false) {
                    if (radio1.isChecked() == true) {

                        Intent i = new Intent(this, MyIntentService.class);
                        i.setAction(MainActivity.DO_INSERT);
                        i.putExtra(MainActivity.KEY_VALUE1, name.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE2, id.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE3, "Male");
                        i.putExtra(MainActivity.KEY_VALUE4, base.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE5, total.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE6, rate.getText().toString());
                        startService(i);

                        name.setText("");
                        id.setText("");
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        base.setText("");
                        total.setText("");
                        rate.setText("");


                    } else if (radio2.isChecked() == true) {

                        Intent i = new Intent(this, MyIntentService.class);
                        i.setAction(MainActivity.DO_INSERT);
                        i.putExtra(MainActivity.KEY_VALUE1, name.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE2, id.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE3, "Female");
                        i.putExtra(MainActivity.KEY_VALUE4, base.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE5, total.getText().toString());
                        i.putExtra(MainActivity.KEY_VALUE6, rate.getText().toString());
                        startService(i);

                        name.setText("");
                        id.setText("");
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        base.setText("");
                        total.setText("");
                        rate.setText("");
                    }
                    NotificationManager man = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationCompat.Builder note = null;

                    createChannel();
                    note = new NotificationCompat.Builder(this, ChannelID)
                            .setAutoCancel(true)
                            .setOngoing(false)
                            .setContentTitle("Insert")
                            .setContentText("Input process has been successfully")
                            .setColor(Color.RED)
                            .setColorized(true)
                            .setShowWhen(true)
                            .setSmallIcon(R.drawable.add)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.emp));
                    man.notify(++NoteId, note.build());
                }
                if (exist == true) {
                    Toast.makeText(this, "This ID Is  Exist....", Toast.LENGTH_LONG).show();
                }
                exist = false;
            }
        }
    }
}
