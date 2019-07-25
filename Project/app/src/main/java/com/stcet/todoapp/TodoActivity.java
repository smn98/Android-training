package com.stcet.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class TodoActivity extends AppCompatActivity {
    EditText item, details;
    TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MainActivity";
    private String mUserName;
    Spinner mstaticSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        item = findViewById(R.id.main_item_et);
        details = findViewById(R.id.main_details_et);
        mDisplayDate = findViewById(R.id.main_date_tv);

        //dropdown
        mstaticSpinner=(Spinner)findViewById(R.id.todo_spinner);

        String[] items = new String[] { "Blue", "Green", "Yellow", "Red" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mstaticSpinner.setAdapter(adapter);
        mstaticSpinner.setSelection(0);
        ////////////////////

        //edit text to todays date
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        String date = formatter.format(today);
        mDisplayDate.setText(date);

        Intent i = getIntent();
        mUserName = i.getStringExtra("UserName");

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    public void AddTask(View view) {
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        try{
            Todo todo = r.createObject(Todo.class);
            todo.setUserName(mUserName);
            todo.setTodoItem(item.getText().toString());
            todo.setDetails(details.getText().toString());
            todo.setDuedate(mDisplayDate.getText().toString());
            todo.setColor(mstaticSpinner.getSelectedItem().toString());
//            RealmQuery<Todo> query = r.where(Todo.class).equalTo("userName", mUserName);
//            RealmResults<Todo> result = query.findAllAsync();
//            result.load();
            r.commitTransaction();
            r.close();
            Toast.makeText(this,"New todo created",Toast.LENGTH_LONG).show();
            finish();
        }
        catch (Exception e){
            r.cancelTransaction();
            Toast.makeText(this,"Failure" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void PickDate(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    public void DiscardTask(View view) {
        finish();
    }
}
