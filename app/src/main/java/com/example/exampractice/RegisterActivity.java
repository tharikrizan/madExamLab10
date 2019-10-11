package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import database.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName , editTextPassword ;
    RadioGroup rg;
    RadioButton rb;
    Button  buttonRegister;

    DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserName = (EditText)findViewById(R.id.editTextRegisterUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextRegisterPassword);
        rg = (RadioGroup)findViewById(R.id.radiogroupRegister);
        buttonRegister =(Button)findViewById(R.id.buttonRegisterRegister);

        dbh = new DBHelper(getApplicationContext());

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                int checkedId=rg.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(checkedId);

                String type = rb.getText().toString().trim();

                long i = dbh.AddNewUser(userName,password,type);

                if( i > 0 ){
                    Toast.makeText(RegisterActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Insert NOT Successful", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }

}
