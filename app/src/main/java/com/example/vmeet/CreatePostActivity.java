package com.example.vmeet;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    boolean  isStartTime=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("New Booking");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        //StartTime

        TextView startTime=(TextView) findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime=true;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        //endtime
        TextView endTime=(TextView ) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime=false;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //EventDate

        TextView datepicker = (TextView) findViewById(R.id.datepicker);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView= (TextView) findViewById(R.id.date);
        textView.setText(currentDateString);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        if(isStartTime) {
            this.statTime(view,hourOfDay, minute);
        }
        else {
            this.endTime(view,hourOfDay,minute);
        }

    }

    @SuppressLint("SetTextI18n")
    private void endTime(TimePicker view, int hourOfDay, int minute) {
        EditText et = (EditText) findViewById(R.id.etime);
        et.setText(hourOfDay + " : " + minute);

    }

    @SuppressLint("SetTextI18n")
    private void statTime(TimePicker view, int hourOfDay, int minute) {
        EditText st=(EditText) findViewById(R.id.stime);
        st.setText(hourOfDay + " : " + minute);

    }
}

