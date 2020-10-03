package com.example.vmeet;

        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.TimePicker;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.DialogFragment;

        import java.text.DateFormat;
        import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    boolean  isStartTime=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);



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

    private void endTime(TimePicker view,int hourOfDay, int minute) {
        EditText et = (EditText) findViewById(R.id.etime);
        et.setText("Hour: " + hourOfDay + " Minute: " + minute);

    }

    private void statTime(TimePicker view,int hourOfDay, int minute) {
        EditText st=(EditText) findViewById(R.id.stime);
        st.setText("Hour: " + hourOfDay + " Minute: " + minute);

    }
}

