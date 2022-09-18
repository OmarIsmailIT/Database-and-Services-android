package com.example.database;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.database.data;

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {

        super("MyIntentService");

        System.out.println("IntentService--> calls COntructor");
    }

    @Override
    public void onCreate() {
        data.db = openOrCreateDatabase("firstDB", MODE_PRIVATE, null);
        data.db.execSQL("create table if not exists emp ( name varchar, id int primary key , sex varchar, baseSalary float , totalSalary float , commissionRate float);");
        System.out.println("IntentService--> calls OnCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {

        System.out.println("IntentService--> calls onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        System.out.println("IntentService--> calls Onstart command()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        System.out.println("IntentService--> calls onHandleIntent");
        if (intent.getAction().equals(MainActivity.KEY_OPEN)) {
            System.out.println("open");

        } else if (intent.getAction().equals(MainActivity.DO_INSERT)) {
            String Name = intent.getStringExtra(MainActivity.KEY_VALUE1);
            String Id = intent.getStringExtra(MainActivity.KEY_VALUE2);
            String Sex = intent.getStringExtra(MainActivity.KEY_VALUE3);
            String Base = intent.getStringExtra(MainActivity.KEY_VALUE4);
            String Total = intent.getStringExtra(MainActivity.KEY_VALUE5);
            String Rate = intent.getStringExtra(MainActivity.KEY_VALUE6);
            int id1 = Integer.parseInt(Id);
            float base1 = Float.parseFloat(Base);
            float total1 = Float.parseFloat(Total);
            float rate1 = Float.parseFloat(Rate);
            data.db.execSQL("insert into emp values (?,?,?,?,?,?);",
                    new Object[]{Name, id1, Sex, base1, total1, rate1});

        } else if (intent.getAction().equals(MainActivity.DO_DELETE)) {
            String Id = intent.getStringExtra(MainActivity.KEY_VALUE2);
            int id1 = Integer.parseInt(Id);
            data.db.execSQL("delete from emp where id=?", new Object[]{id1});

        } else if (intent.getAction().equals(MainActivity.DO_SEARCH)) {
            String Id = intent.getStringExtra(MainActivity.KEY_VALUE2);
            int id1 = Integer.parseInt(Id);
            int exist = 0;

            Cursor rs = data.db.rawQuery("select *  from emp  where id = ? ;",
                    new String[]{String.valueOf(id1)});
            String n = null,s = null;
            int i = 0;
            float b = 0,t=0,r=0;

            while (rs.moveToNext()) {
                if (rs.getInt(1) == Integer.parseInt(String.valueOf(id1))) {
                    exist = 1;
                     n = rs.getString(0);
                     i = rs.getInt(1);
                     s = rs.getString(2);
                     b = rs.getFloat(3);
                     t = rs.getFloat(4);
                     r = rs.getFloat(5);
                }
                else if(rs.getInt(1) != Integer.parseInt(String.valueOf(id1))){
                    exist=2;
                }
            }

            Intent  xxx = new Intent ();
                if (exist == 1) {

                    xxx.setAction( MainActivity.RESULT_SEARCH_BACK );
                    xxx.putExtra (MainActivity.KEY_TOAST   ,exist);
                    xxx.putExtra ( MainActivity.KEY_VALUE1 , n   );
                    xxx.putExtra ( MainActivity.KEY_VALUE2 , i   );
                    xxx.putExtra ( MainActivity.KEY_VALUE3 , s   );
                    xxx.putExtra ( MainActivity.KEY_VALUE4 , b   );
                    xxx.putExtra ( MainActivity.KEY_VALUE5 , t   );
                    xxx.putExtra ( MainActivity.KEY_VALUE6 , r   );
                }

            else if (exist==2){
                    xxx.putExtra(MainActivity.KEY_TOAST,exist);
            }
            sendBroadcast ( xxx );
        }
        else if(intent.getAction().equals(MainActivity.DO_MODIFY)){

            String Name = intent.getStringExtra(MainActivity.KEY_VALUE1);
            String Id = intent.getStringExtra(MainActivity.KEY_VALUE2);
            String Sex = intent.getStringExtra(MainActivity.KEY_VALUE3);
            String Base = intent.getStringExtra(MainActivity.KEY_VALUE4);
            String Total = intent.getStringExtra(MainActivity.KEY_VALUE5);
            String Rate = intent.getStringExtra(MainActivity.KEY_VALUE6);
            int id1 = Integer.parseInt(Id);
            float base1 = Float.parseFloat(Base);
            float total1 = Float.parseFloat(Total);
            float rate1 = Float.parseFloat(Rate);
            data.db.execSQL("Update emp Set name ='" + Name
                    + "',sex='" + Sex
                    + "',baseSalary=" + base1
                    + ",totalSalary=" + total1
                    + ",commissionRate=" + rate1
                    + " where id=" + id1 + ";");
        }
        else if (intent.getAction().equals(MainActivity.DO_DISPLAY)){
            Intent  xx = new Intent ();
            Cursor rs = data.db.rawQuery("select * from emp ;", null);
            while (rs.moveToNext()) {
                display.examplelist.add(new ExampleItem(R.drawable.emp,
                        rs.getString(0).toString(),
                        rs.getString(1).toString(),
                        rs.getString(2).toString(),
                        rs.getString(3).toString(),
                        rs.getString(4).toString(),
                        rs.getString(5).toString()));
            }
            xx.setAction( MainActivity.RESULT_DISPLAY_BACK );
            sendBroadcast ( xx );
        }
    }
}



