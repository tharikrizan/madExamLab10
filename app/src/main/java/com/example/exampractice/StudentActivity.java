package com.example.exampractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    TextView textViewWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent i = getIntent();
        String userName = i.getStringExtra("userName");


        textViewWelcome= (TextView)findViewById(R.id.textViewStudentWelcome);

        textViewWelcome.setText("Welcome "+userName);
    }
}
