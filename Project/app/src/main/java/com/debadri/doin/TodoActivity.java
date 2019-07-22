package com.debadri.doin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TodoActivity extends AppCompatActivity {
    EditText item, details, color;
    TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        item = findViewById(R.id.main_item_et);
        details = findViewById(R.id.main_details_et);
        color = findViewById(R.id.main_color_et);
        mDisplayDate = findViewById(R.id.main_date_tv);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    public void AddTask(View view) {
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        try{
            Todo todo = new Todo();
            todo.setUserName("sumon");
            todo.setTodoItem(item.getText().toString());
            todo.setDetails(details.getText().toString());
            todo.setDuedate(mDisplayDate.getText().toString());
            todo.setColor(color.getText().toString());
            RealmQuery<UserInfo> query = r.where(UserInfo.class).equalTo("userName", MainActivity.mUserName.getText().toString());
            RealmResults<UserInfo> result = query.findAllAsync();
            result.load();
            r.commitTransaction();
            r.close();
            Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show();
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
        Intent i=new Intent(this,TaskActivity.class);
        startActivity(i);
    }
}
