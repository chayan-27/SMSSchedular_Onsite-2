package com.example.smsschedular;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText phone;
    EditText body;
    TextView dt;
    TextView tm;
    Button sch;
    int hour,minute1;
    Calendar calendar1 ;
    ImageButton imageButton;
    DataBase dataBase;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = (EditText) findViewById(R.id.phone);
        body = (EditText) findViewById(R.id.body);
        dt = (TextView) findViewById(R.id.dt);
        tm = (TextView) findViewById(R.id.tm);
        sch = (Button) findViewById(R.id.sch);


        imageButton=(ImageButton)findViewById(R.id.search);
        dataBase=DataBase.getInstance(getApplicationContext());
        calendar1 = Calendar.getInstance();
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });
        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(body.getText().toString().equals("")||phone.getText().toString().equals("")||dt.getText().toString().equals("Select Date")||tm.getText().toString().equals("Select Time"))){
                    sendSms(phone.getText().toString(),body.getText().toString(),dt.getText().toString()+" "+tm.getText().toString());
                }else{
                    Toast.makeText(MainActivity.this, "Enter Valid Details", Toast.LENGTH_LONG).show();

                }
            }
        });



        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (!(check == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);

        }



    }

    public void sendSms(final String address, final String message, final String time) {


        Intent intent = new Intent(this, Notification_receiver.class);
        intent.putExtra("address",address);
        intent.putExtra("message",message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) ((Math.random() * 65500)), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                if(name==null){
                    name="Unknown";
                }
                dataBase.getDao().insert(new LiveTest(name+"\n"+address,message,time,calendar1.getTimeInMillis()));

            }
        });
        thread.start();
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // sendSms();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);

            }
        }
    }

    public void showTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                minute1=minute;
                String y = "";
                if (hourOfDay > 0 && hourOfDay < 12) {
                    y = "AM";

                    if (minute >= 0 && minute <= 9) {
                        tm.setText(hourOfDay + ":0" + minute + " " + y);
                    } else {
                        tm.setText(hourOfDay + ":" + minute + " " + y);
                    }


                } else if (hourOfDay > 12) {
                    y = "PM";
                    if (minute >= 0 && minute <= 9) {
                        tm.setText((hourOfDay - 12) + ":0" + minute + " " + y);
                    } else {
                        tm.setText((hourOfDay - 12) + ":" + minute + " " + y);
                    }


                } else if (hourOfDay == 12) {
                    y = "PM";
                    if (minute >= 0 && minute <= 9) {
                        tm.setText(12 + ":0" + minute + " " + y);
                    } else {
                        tm.setText(12 + ":" + minute + " " + y);
                    }


                } else {
                    y = "AM";
                    if (minute >= 0 && minute <= 9) {
                        tm.setText(12 + ":0" + minute + " " + y);
                    } else {
                        tm.setText(12 + ":" + minute + " " + y);
                    }
                }

                calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar1.set(Calendar.MINUTE, minute);



            }
        }, h, m, false);
        timePickerDialog.show();

    }
    public void showDatePicker() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String timee;
                if (month >= 0 && month <= 8) {
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                         timee = "0" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                        //timee = year + "-0" + (month + 1) + "-0" + dayOfMonth;


                    } else {
                        timee = dayOfMonth + "-0" + (month + 1) + "-" + year;
                        //timee = year + "-0" + (month + 1) + "-" + dayOfMonth;

                    }
                } else {
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                         timee = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;
                       // timee = year + "-" + (month + 1) + "-0" + dayOfMonth;

                    } else {
                        timee = dayOfMonth + "-" + (month + 1) + "-" + year;
                        //timee = year + "-" + (month + 1) + "-" + dayOfMonth;

                    }

                }

                dt.setText(timee);
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar1.set(Calendar.MONTH, month);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );


        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (1) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                         name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phone.setText(number);
                    }
                }
                break;
        }
    }
}
