package com.debadri.doin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public static TextInputEditText mUserName;
    private Button mLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName=(TextInputEditText)findViewById(R.id.main_userName);
        mLoginBtn=(Button)findViewById(R.id.main_btn);

        Realm realm = Realm.getDefaultInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RealmResults<UserInfo> results = realm.where(UserInfo.class).findAllAsync();
                if(mUserName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Am I a joke to you?", Toast.LENGTH_LONG).show();
                }
                else {
                    RealmQuery<UserInfo> query = realm.where(UserInfo.class).equalTo("userName", mUserName.getText().toString());
                    RealmResults<UserInfo> result = query.findAllAsync();
                    result.load();

                    if (result.size() == 0) {
                        realm.beginTransaction();
                        UserInfo obj = realm.createObject(UserInfo.class);
                        obj.setUserName(mUserName.getText().toString());
                        obj.setOthers("lol");
                        realm.commitTransaction();

                        Toast.makeText(MainActivity.this, "New User Added", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Welcome again", Toast.LENGTH_LONG).show();
                    }

                    Intent intent=new Intent(MainActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}
